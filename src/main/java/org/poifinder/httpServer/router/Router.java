package org.poifinder.httpServer.router;

import java.util.HashMap;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.poifinder.httpServer.auth.AuthMiddleware;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    public Router(AuthMiddleware authMiddleware) {
        this.routes = new HashMap<String, Route>();
        this.authMiddleware = authMiddleware;
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
        authMiddleware.setCurrentUser(request);
        String path = request.getRequestURI();
        String matchedRoute = matchRoute(path);
        if (matchedRoute != null) {
            try {
                Route route = routes.get(matchedRoute);
                if(route == null)
                    throw new NullPointerException("La rotta non risulta definita");
                if (this.checkAuth(route)){
                    return true;
                }else {
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    response.getWriter().write("Accesso non autorizzato: Permessi insufficienti per la rotta " + matchedRoute);
                    return false;
                }
            }catch (Exception e) {
                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    response.getWriter().write("Errore interno durante l'elaborazione della richiesta: " + e.getMessage());
                    e.printStackTrace();
                    return false;
            }
        }else{
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().write("404 Risorsa non trovata ");
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

