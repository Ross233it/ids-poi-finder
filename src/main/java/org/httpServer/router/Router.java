package org.httpServer.router;

import com.sun.net.httpserver.HttpExchange;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.controllers.Controller;
import org.controllers.ControllerFactory;

/**
 * Questa classe definisce tutti gli end point raggiungibili nella piattaforma.
 */
public class Router {

    private Map<String, Route> routes;

    private HttpExchange exchange;

    public Router(HttpExchange exchange) {
        this.exchange = exchange;
        this.routes = new HashMap<String, Route>();
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
            case "DELETE":
                this.routes = routesApi.setUpDeleteRoutes();
                break;
            default:
        }
    }

    /**
     * Riceve una rotta chiamante dall'handler e si occupa di richiamare
     * controller e metodo appropriati
     * @param path String la rotta chiamata
     */
    public String dispatch(String path) {
        if (routes.containsKey(path)) {
            Route route = routes.get(path);

            String methodName = route.getMethodName();
            String controllerName = route.getControllerName();

            try {
                ControllerFactory factory = new ControllerFactory();
                Controller controllerInstance = factory.createController(controllerName, this.exchange);
                Method method = controllerInstance.getClass().getMethod(methodName);
                return (String) method.invoke(controllerInstance);
            } catch (Exception e) {
                e.printStackTrace();
                return "Errore nel router: " + e.getMessage();
            }
        }
        return "404 Not Found";
    }
}

