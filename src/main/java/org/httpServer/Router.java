package org.httpServer;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.controllers.*;

import java.util.ArrayList;
import java.util.List;

public class Router {

    private List<Route> routes;

    public Router() {
        routes = new ArrayList<>();
        this.setUpRoutes("api");
    }

    /**
     * Add a route to routes list
     * @param path the endpoint to reach the route
     * @param handler the handler to menage the request
     */
    private void addRoute(String path, HttpHandler handler) {
        routes.add(new Route(path, handler));
    }

    /**
     * Register a group of routes to a server.
     * @param server
     */
   public void registerRoutes(HttpServer server) {
        for (Route route : routes) {
            server.createContext(route.getPath(), route.getHandler());
        }
    }

    /**
     * Defines all routes of the system for registration
     * @param prefix
     */
    private void setUpRoutes(String prefix){
        ControllerFactory factory = new ControllerFactory();
        this.addRoute("/"+prefix+"/poi" ,               factory.createController("PoiController"));
        this.addRoute("/"+prefix+"/municipality",       factory.createController("MunicipalityController"));
        this.addRoute("/"+prefix+"/user",               factory.createController("RegisteredUserController"));
        this.addRoute("/"+prefix+"/user/set-role",   factory.createController("RegisteredUserController"));
        this.addRoute("/"+prefix+"/user/login",         factory.createController("RegisteredUserController"));
        this.addRoute("/"+prefix+"/migrate",            new MigrationsController());
    }
}

