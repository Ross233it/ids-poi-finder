package org.poifinder.httpServer.router;

import java.util.HashMap;
import java.util.Map;

public class RoutesApi {

    /**
     * La lista delle rotte disponibili
     */
    private Map<String, Route> routes;

    /**
     * Il prefisso delle rotte
     */
    private String prefix;

    public RoutesApi() {
        this.routes = new HashMap<String, Route>();
        this.prefix = "api";
    }

    /**
     * Aggiunge una rotta alla lista degli endpoint
     * @param path l'endpoint raggiungibile dalla richiesta
     * @param controllerName String il nome del controller che gestirà la chiamata
     * @param method String il nome del metodo richiamato
     * @param authLevel int il livello di privilegi richiesto per accedere alla rotta
     */
    private void addRoute(String path,
                          String controllerName,
                          String method,
                          int authLevel) {
        routes.put(path, new Route(path, controllerName, method, authLevel));
    }

    /**
     * Ritorna la lista delle rotte
     */
    private Map<String, Route> getRoutes(){
        return this.routes;
    }

    /**
     * Rotte registrate per il metodo http GET
     * @return Map ritorna una serie di rotte con i relativi metodi e permessi
     */
    public Map<String, Route> setUpGetRoutes(){
        this.addRoute("/"+prefix+"/poi" ,         "PoiController", "index", 5);
        this.addRoute("/"+prefix+"/poi/{id}" ,         "PoiController", "show", 5);
        this.addRoute("/"+prefix+"/municipality", "MunicipalityController", "index", 5);
        this.addRoute("/"+prefix+"/municipality/{id}", "MunicipalityController", "show", 5);
        this.addRoute("/"+prefix+"/user",         "RegisteredUserController", "index", 5);
        this.addRoute("/"+prefix+"/user/{id}",         "RegisteredUserController", "show", 1);
        this.addRoute("/"+prefix+"/activity/{id}",     "ActivityController", "show", 5);
        this.addRoute("/"+prefix+"/migrate",      "migrationController", "index", 5);

        this.addRoute("/"+prefix+"/favorites",      "FavoriteController", "index", 4);
        this.addRoute("/"+prefix+"/favorites/{id}/handle",      "FavoriteController", "handleFavorite", 4);

        return this.getRoutes();
    }

    /**
     * Rotte registrate per il metodo http POST
     * @return Map ritorna una serie di rotte con i relativi metodi e permessi
     */
    public Map<String, Route> setUpPostRoutes(){
        this.addRoute("/"+prefix+"/user/login",        "RegisteredUserController", "login", 5);
        this.addRoute("/"+prefix+"/user/logout",       "RegisteredUserController", "logout", 4);
        this.addRoute("/"+prefix+"/user/register",         "RegisteredUserController", "create", 5);

        this.addRoute("/"+prefix+"/activity/experience",        "RegisteredUserController", "login", 2);
        this.addRoute("/"+prefix+"/activity/contest",       "RegisteredUserController", "logout", 2);
        this.addRoute("/"+prefix+"/activity/itinerary",         "RegisteredUserController", "create", 2);
        this.addRoute("/"+prefix+"/activity/{id}/add",     "ActivityController", "addPois",  4);
        this.addRoute("/"+prefix+"/activity/{id}",     "ActivityController", "index",  2);

        this.addRoute("/"+prefix+"/municipality", "MunicipalityController", "create", 1);
        this.addRoute("/"+prefix+"/poi" ,         "PoiController", "create", 4);
        this.addRoute("/"+prefix+"/poi/{id}/report-content" ,         "PoiController", "create", 5);

        this.addRoute("/"+prefix+"/migrate/{id}",      "migrationController", "index",  1);
        return this.getRoutes();
    }
    public Map<String, Route> setUpPutRoutes(){
        return this.getRoutes();
    }

    /**
     * Rotte registrate per il metodo http PATCH
     * @return Map ritorna una serie di rotte con i relativi metodi e permessi
     */
    public Map<String, Route> setUpPatchRoutes(){
        this.addRoute("/"+prefix+"/user/{id}/update",         "RegisteredUserController", "update",4);
        this.addRoute("/"+prefix+"/user/{id}/set-role",     "RegisteredUserController", "setRole", 1);

        this.addRoute("/"+prefix+"/poi" ,         "PoiController", "index", 1);
        this.addRoute("/"+prefix+"/poi/{id}" ,         "PoiController", "edit", 4);
        this.addRoute("/"+prefix+"/poi/{id}/validate" ,         "PoiController", "validate", 3);
        this.addRoute("/"+prefix+"/poi/{id}/reject" ,         "PoiController", "validate", 3);

        this.addRoute("/"+prefix+"/municipality/{id}", "MunicipalityController", "index", 1);

        this.addRoute("/"+prefix+"/activity",     "ActivityController", "index", 1);
        this.addRoute("/"+prefix+"/migrate",      "migrationController", "index", 1);
        return this.getRoutes();
    }

    /**
     * Rotte registrate per il metodo http DELETE
     * @return Map ritorna una serie di rotte con i relativi metodi e permessi
     */
    public Map<String, Route> setUpDeleteRoutes(){
        this.addRoute("/"+prefix+"/poi/{id}" ,         "PoiController", "delete",1);
        this.addRoute("/"+prefix+"/municipality/{id}", "MunicipalityController", "delete",1);
        this.addRoute("/"+prefix+"/user/{id}",         "RegisteredUserController", "delete",4);
        this.addRoute("/"+prefix+"/activity/{id}",     "ActivityController", "delete",1);
        return this.getRoutes();
    }
}
