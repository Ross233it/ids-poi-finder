package org.httpServer;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.controllers.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Questa classe definisce tutti gli end point raggiungibili nella piattaforma.
 */
public class Router {

    private List<Route> routes;

    public Router() {
        routes = new ArrayList<>();
        this.setUpRoutes("api");
    }

    /**
     * Aggiunge una rotta alla lista degli endpoint
     * @param path l'endpoint raggiungibile dalla richiesta
     * @param handler l'handler http per gestire la rotta
     */
    private void addRoute(String path, HttpHandler handler) {
        routes.add(new Route(path, handler));
    }

    /**
     * Registra un insieme di rotte nel server
     * @param server il server che utilizzerà le rotte.
     */
   public void registerRoutes(HttpServer server) {
        for (Route route : routes) {
            server.createContext(route.getPath(), route.getHandler());
        }
    }

    /**
     * Definisce tutte le rotte raggiungibili nella piattaforma ed il relativo
     * handler -
     * Ogni controller può personalizzare le sue rotte definendo il proprio setup.
     * @param prefix il prefisso della rotta - "api" in questo contesto
     */
    private void setUpRoutes(String prefix){
        HttpHandlerFactory factory = new HttpHandlerFactory();
//        "/api/user/set-role"
        this.addRoute("/"+prefix+"/poi" ,            factory.createHandler("PoiController"));
        this.addRoute("/"+prefix+"/municipality",    factory.createHandler("MunicipalityController"));
        this.addRoute("/"+prefix+"/user",            factory.createHandler("RegisteredUserController"));
        this.addRoute("/"+prefix+"/user/set-role",   factory.createHandler("RegisteredUserController"));
        this.addRoute("/"+prefix+"/user/login",      factory.createHandler("RegisteredUserController"));
        this.addRoute("/"+prefix+"/activity",        factory.createHandler("ActivityController"));
        this.addRoute("/"+prefix+"/migrate",         new MigrationsController());
    }

}

