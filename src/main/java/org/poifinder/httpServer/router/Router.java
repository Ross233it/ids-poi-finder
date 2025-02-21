package org.poifinder.httpServer.router;

import java.util.HashMap;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.poifinder.httpServer.auth.AuthMiddleware;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;


/**
 * Questa classe definisce verifica i permessi di accesso alle rotte ed
 * istrada le richieste.
 */
@Component
public class Router implements HandlerInterceptor {

    private Map<String, Route> routes;

    private AuthMiddleware authMiddleware;

    public Router() {
        this.routes = new HashMap<String, Route>();
        this.authMiddleware = new AuthMiddleware();
    }

    /**
     * Inizializza le rotte in base al metodo della request.
     * Consente di gestire azioni diverse in base al metodo.
     */
    private void routesInit(HttpServletRequest request){
        String requestMethod = request.getMethod().toUpperCase();
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
        return authMiddleware.hasPermissions(requiredAuthLevel);
    }

    /**
     * In base ad una richiesta ricevuta valuta se la rotta esiste
     * e la istrada se l'utente corrente ha i permessi necessari
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        this.routesInit(request);
        String path = request.getRequestURI();
        String matchedRoute = matchRoute(path);
        Route route = null;
        if (matchedRoute != null) {
            route = routes.get(matchedRoute);
        }else{
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().write("404 Risorsa non trovata ");
            return false;
        }
        if (this.checkAuth(route)){
            return true;
        }else {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("Accesso non autorizzato: Permessi insufficienti per la rotta " + matchedRoute);
            return false;
        }
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

