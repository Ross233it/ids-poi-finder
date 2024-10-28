package org.httpServer;

import com.sun.net.httpserver.HttpHandler;

public class Route {
    private String path;

    private HttpHandler handler;

    /**
     * Create a new string
     * @param path the endpoint for requests
     * @param handler the handler for the route
     */
    public Route(String path, HttpHandler handler) {
            this.path = path;
            this.handler = handler;
    }

    /**
     * Returns endpoint for http requests
     * @return
     */
    public String getPath() {
        return path;
    }

    /**
     * Returns the handler of a routes
     * @return
     */
    public HttpHandler getHandler() {
        return handler;
    }
}



