package org.httpServer;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.controllers.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Questa classe definisce tutti gli end point raggiungibili nella piattaforma.
 */
public class Router {

    private Map<String, Route> routes;

    public Router() {
        routes = new HashMap<String, Route>();
        this.setUpRoutes("api");
    }

    /**
     * Aggiunge una rotta alla lista degli endpoint
     * @param path l'endpoint raggiungibile dalla richiesta
     * @param handler l'handler http per gestire la rotta
     */
    private void addRoute(String path, String controllerName, String method) {
        routes.put(path, new Route(path, controllerName, method));
    }

    /**
     * Riceve una rotta chiamante dall'handler e si occupa di richiamare
     * controller e metodo appropriati
     * @param path String la rotta chiamata
     * @param exchange l'oggetto che rappresenta la richiesta http.
     */
    public String dispatch(String path, HttpExchange exchange) {
        if (routes.containsKey(path)) {
            Route route = routes.get(path);

            String methodName = route.getMethod();
            String controllerName = route.getControllerName();

            try {
                Class<?> controllerClass = Class.forName(controllerName);
                Constructor<?> constructor = controllerClass.getDeclaredConstructor(HttpExchange.class);
                Object controllerInstance  = constructor.newInstance(exchange);
                Method method = controllerClass.getMethod(methodName);
                return (String) method.invoke(controllerInstance);
            } catch (Exception e) {
                e.printStackTrace();
                return "Errore nel router: " + e.getMessage();
            }
        }
        return "404 Not Found";
    }

    /**
     * Definisce tutte le rotte raggiungibili nella piattaforma ed il relativo
     * handler
     * @param prefix il prefisso della rotta - "api" in questo contesto
     */
//    private void setUpRoutes(String prefix){
//        HttpHandlerFactory factory = new HttpHandlerFactory();
//        this.addRoute("/"+prefix+"/poi" ,         factory.createHandler("PoiController"), "index");
//        this.addRoute("/"+prefix+"/municipality", factory.createHandler("MunicipalityController"), "index");
//        this.addRoute("/"+prefix+"/user",         factory.createHandler("RegisteredUserController"), "index");
//        this.addRoute("/"+prefix+"/user/set-role",factory.createHandler("RegisteredUserController"), "index");
//        this.addRoute("/"+prefix+"/user/login",   factory.createHandler("RegisteredUserController"), "index");
//        this.addRoute("/"+prefix+"/user/logout",  factory.createHandler("RegisteredUserController"), "index");
//        this.addRoute("/"+prefix+"/activity",     factory.createHandler("ActivityController"), "index");
//        this.addRoute("/"+prefix+"/migrate",      new MigrationsController(), "index");
//    }

    private void setUpRoutes(String prefix){
        this.addRoute("/"+prefix+"/poi" ,         "PoiController", "index");
        this.addRoute("/"+prefix+"/municipality", "MunicipalityController", "index");
        this.addRoute("/"+prefix+"/user",         "RegisteredUserController", "index");
        this.addRoute("/"+prefix+"/user/set-role","RegisteredUserController", "index");
        this.addRoute("/"+prefix+"/user/login",   "RegisteredUserController", "index");
        this.addRoute("/"+prefix+"/user/logout",  "RegisteredUserController", "index");
        this.addRoute("/"+prefix+"/activity",     "ActivityController", "index");
        this.addRoute("/"+prefix+"/migrate",      "migrationController", "index");
    }

}

