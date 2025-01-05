package org.httpServer;

import com.sun.net.httpserver.HttpHandler;

/**
 * Questa classe ha la responsabilità di rappresentare le rotte raggiungibili
 * tramite richieste http.
 */
public class Route {
    private String path;

    private HttpHandler handler;

    /**
     * Crea una nuova rotta
     * @param path il percorso da contattare per la richiesta
     * @param handler il gestore http per la rotta.
     */
    public Route(String path, HttpHandler handler) {
            this.path = path;
            this.handler = handler;
    }

    /**
     * Ritorna l'endpoint della rotta
     * @return String path
     */
    public String getPath() {
        return path;
    }

    /**
     * Ritorna l'handler correlato alla rotta
     * @return
     */
    public HttpHandler getHandler() {
        return handler;
    }
}



