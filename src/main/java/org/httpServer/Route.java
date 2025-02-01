package org.httpServer;

import com.sun.net.httpserver.HttpHandler;

/**
 * Questa classe ha la responsabilità di rappresentare le rotte raggiungibili
 * tramite richieste http.
 */
public class Route {

    /**
     * L'end point della rotta
     */
    private String path;

    /**
     * Il metodo che gestisce le richieste sulla rotta
     */
    private String controllerName;

    /**
     * Il metodo eseguito quando viene richiamata la rotta
     */
    private String method;

    /**
     * Crea una nuova rotta
     * @param path il percorso da contattare per la richiesta
     * @param
     */
    public Route(String path, String controllerName, String method) {
            this.path       = path;
            this.controllerName = controllerName;
            this.method     = method;
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
    public String getControllerName() { return controllerName; }

    /**
     * Ritorna l'endpoint della rotta
     * @return String path
     */
    public String getMethod() {
        return path;
    }
}



