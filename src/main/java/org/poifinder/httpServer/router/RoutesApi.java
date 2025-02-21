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
     * Costruisce la struttura dati che contiene le informazioni di associazione fra rotte, controller e metodi
     */
    public Map<String, Route> setUpGetRoutes(){
        this.addRoute("/"+prefix+"/poi" ,         "PoiController", "index", 5);
        this.addRoute("/"+prefix+"/poi/{id}" ,         "PoiController", "show", 5);
        this.addRoute("/"+prefix+"/municipality", "MunicipalityController", "index", 5);
        this.addRoute("/"+prefix+"/municipality/{id}", "MunicipalityController", "show", 5);
        this.addRoute("/"+prefix+"/user",         "RegisteredUserController", "index", 5);
        this.addRoute("/"+prefix+"/user/{id}",         "RegisteredUserController", "show", 1);
        this.addRoute("/"+prefix+"/activity",     "ActivityController", "index", 5);
        this.addRoute("/"+prefix+"/migrate",      "migrationController", "index", 5);
        return this.getRoutes();
    }

    public Map<String, Route> setUpPostRoutes(){
        this.addRoute("/"+prefix+"/user/login",        "RegisteredUserController", "login", 5);
        this.addRoute("/"+prefix+"/user/logout",       "RegisteredUserController", "logout", 4);
        this.addRoute("/"+prefix+"/municipality", "MunicipalityController", "create", 1);
        this.addRoute("/"+prefix+"/poi" ,         "PoiController", "create", 4);
        this.addRoute("/"+prefix+"/register",         "RegisteredUserController", "create", 5);
        this.addRoute("/"+prefix+"/activity/{id}",     "ActivityController", "index",  2);
        this.addRoute("/"+prefix+"/migrate/{id}",      "migrationController", "index",  1);
        return this.getRoutes();
    }
    public Map<String, Route> setUpPutRoutes(){
        return this.getRoutes();
    }

    public Map<String, Route> setUpPatchRoutes(){
        this.addRoute("/"+prefix+"/user/set-role",     "RegisteredUserController", "setRole", 1);
        this.addRoute("/"+prefix+"/poi" ,         "PoiController", "index", 1);
        this.addRoute("/"+prefix+"/poi/{id}/validate" ,         "PoiController", "setStatus", 3);
        this.addRoute("/"+prefix+"/municipality", "MunicipalityController", "index", 1);
        this.addRoute("/"+prefix+"/user",         "RegisteredUserController", "index", 1);
        this.addRoute("/"+prefix+"/activity",     "ActivityController", "index", 1);
        this.addRoute("/"+prefix+"/migrate",      "migrationController", "index", 1);
        return this.getRoutes();
    }

    public Map<String, Route> setUpDeleteRoutes(){
        this.addRoute("/"+prefix+"/poi/{id}" ,         "PoiController", "delete",1);
        this.addRoute("/"+prefix+"/municipality/{id}", "MunicipalityController", "delete",1);
        this.addRoute("/"+prefix+"/user/{id}",         "RegisteredUserController", "delete",1);
        this.addRoute("/"+prefix+"/activity/{id}",     "ActivityController", "delete",1);
        return this.getRoutes();
    }
}
