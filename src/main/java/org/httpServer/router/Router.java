package org.httpServer.router;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.controllers.Controller;
import org.controllers.ControllerFactory;
import org.httpServer.auth.AuthMiddleware;
import org.httpServer.http.HttpRequest;
import org.httpServer.http.HttpResponses;

/**
 * Questa classe definisce tutti gli end point raggiungibili nella piattaforma.
 */
public class Router {

    private Map<String, Route> routes;

    private HttpExchange exchange;

    private AuthMiddleware authMiddleware;

    public Router(HttpExchange exchange) {
        this.exchange = exchange;
        this.routes = new HashMap<String, Route>();
        this.authMiddleware = new AuthMiddleware();
        this.routesInit();
    }

    /**
     * Inizializza le rotte in base al metodo della request.
     * Consente di gestire azioni diverse in base al metodo.
     */
    private void routesInit(){
        String requestMethod = exchange.getRequestMethod().toUpperCase();
        RoutesApi routesApi = new RoutesApi();
        switch (requestMethod) {
            case "GET":
                this.routes = routesApi.setUpGetRoutes();
                break;
            case "POST":
                this.routes = routesApi.setUpPostRoutes();
                break;
            case "PATCH":
                this.routes = routesApi.setUpPatchRoutes();
                break;
            case "PUT":
                this.routes = routesApi.setUpPutRoutes();
                break;
            case "DELETE":
                this.routes = routesApi.setUpDeleteRoutes();
                break;
            default:
        }
    }

    /**
     * Verifica se l'utente dispone dei privilegi necessari
     * @param route
     * @return
     */
    private boolean checkAuth(Route route) {
        Integer requiredAuthLevel = route.getAuthLevel();
        return authMiddleware.hasPermissions(this.exchange, requiredAuthLevel);
    }

    /**
     * Riceve una rotta chiamante dall'handler e si occupa di richiamare
     * controller e metodo appropriati
     * @param path String la rotta chiamata
     */
    public void dispatch(String path) throws IOException{
        String matchedRoute = matchRoute(path);

        if (matchedRoute != null) {
            Route route = routes.get(matchedRoute);
            String methodName = route.getMethodName();
            String controllerName = route.getControllerName();

            if(!this.checkAuth(route))
                HttpResponses.error(this.exchange, 401, "Non sei autorizzato ad accedere alla risorsa");

            try {
                HttpRequest httpRequest = new HttpRequest(this.exchange);
                httpRequest.setPathParams(matchedRoute);

                ControllerFactory factory = new ControllerFactory();
                Controller controllerInstance = factory.createController(controllerName, httpRequest);

                Method method = controllerInstance.getClass().getMethod(methodName);
                method.invoke(controllerInstance);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        HttpResponses.error(this.exchange, 404, "Risorsa non trovata");
    }

    /**
     * Confronta la rotta impostata nell'oggetto route con parametri dinamici {param}
     * e quelli effettivi. Ritorna la stringa corrispondente o null altrimenti.
     * @param path String la url reale
     * @return registeredRoute la rotta registrata nel server
     */
    private String matchRoute(String path) {
        for (String registeredRoute : routes.keySet()) {
            String regex = registeredRoute.replaceAll("\\{\\w+\\}", "(\\\\w+)");
            if (path.matches(regex)) {
                return registeredRoute;
            }
        }
        return null;
    }
}

