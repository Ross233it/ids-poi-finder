package org.httpServer;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.controllers.MunicipalityController;
import org.migrations.MigrationsHandler;

import java.util.ArrayList;
import java.util.List;

public class Routes {

    private List<Route> routes;

    public Routes() {
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

    private void setUpRoutes(String prefix){
        this.addRoute("/"+prefix+"/municipality", new MunicipalityController());
        this.addRoute("/"+prefix+"/connection", new MunicipalityController());



        this.addRoute("/"+prefix+"/migrate", new MigrationsHandler());
    }
}

