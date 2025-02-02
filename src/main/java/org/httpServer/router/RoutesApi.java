package org.httpServer.router;

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
     */
    private void addRoute(String path, String controllerName, String method) {
        routes.put(path, new Route(path, controllerName, method));
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
        this.addRoute("/"+prefix+"/poi" ,         "PoiController", "index");
        this.addRoute("/"+prefix+"/municipality", "MunicipalityController", "index");
        this.addRoute("/"+prefix+"/user",         "RegisteredUserController", "index");
        this.addRoute("/"+prefix+"/user/set-role","RegisteredUserController", "index");
        this.addRoute("/"+prefix+"/user/login",   "RegisteredUserController", "index");
        this.addRoute("/"+prefix+"/user/logout",  "RegisteredUserController", "index");
        this.addRoute("/"+prefix+"/activity",     "ActivityController", "index");
        this.addRoute("/"+prefix+"/migrate",      "migrationController", "index");
        return this.getRoutes();
    }

    public Map<String, Route> setUpPostRoutes(){
        this.addRoute("/"+prefix+"/poi/{id}" ,         "PoiController", "index");
        this.addRoute("/"+prefix+"/municipality/{id}", "MunicipalityController", "index");
        this.addRoute("/"+prefix+"/user/{id}",         "RegisteredUserController", "index");
        this.addRoute("/"+prefix+"/user/set-role/{id}","RegisteredUserController", "index");
        this.addRoute("/"+prefix+"/user/login/{id}",   "RegisteredUserController", "index");
        this.addRoute("/"+prefix+"/user/logout/{id}",  "RegisteredUserController", "index");
        this.addRoute("/"+prefix+"/activity/{id}",     "ActivityController", "index");
        this.addRoute("/"+prefix+"/migrate/{id}",      "migrationController", "index");
        return this.getRoutes();
    }

    public Map<String, Route> setUpPatchRoutes(){
        this.addRoute("/"+prefix+"/poi" ,         "PoiController", "index");
        this.addRoute("/"+prefix+"/municipality", "MunicipalityController", "index");
        this.addRoute("/"+prefix+"/user",         "RegisteredUserController", "index");
        this.addRoute("/"+prefix+"/user/set-role","RegisteredUserController", "index");
        this.addRoute("/"+prefix+"/user/login",   "RegisteredUserController", "index");
        this.addRoute("/"+prefix+"/user/logout",  "RegisteredUserController", "index");
        this.addRoute("/"+prefix+"/activity",     "ActivityController", "index");
        this.addRoute("/"+prefix+"/migrate",      "migrationController", "index");
        return this.getRoutes();
    }

    public Map<String, Route> setUpDeleteRoutes(){
        this.addRoute("/"+prefix+"/poi/{id}" ,         "PoiController", "delete");
        this.addRoute("/"+prefix+"/municipality/{id}", "MunicipalityController", "delete");
        this.addRoute("/"+prefix+"/user/{id}",         "RegisteredUserController", "delete");
        this.addRoute("/"+prefix+"/activity/{id}",     "ActivityController", "delete");
        return this.getRoutes();
    }
}
