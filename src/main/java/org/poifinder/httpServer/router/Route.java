package org.poifinder.httpServer.router;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

/**
 * Questa classe ha la responsabilità di rappresentare le rotte raggiungibili
 * tramite richieste http.
 */
@Getter
@Setter
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
    private String methodName;


    /**
     * Il livello di privilegi richiesti per accedere alla rotta.
     */
    private int authLevel;

    /**
     * La lista di eventuali parametri passati come query string
     */
    private HashMap<String, String>params;

    /**
     * L'id dell'oggetto trasmesso dal frontend nell'url della
     * chiamata.
     */
    private Integer idParam;

    /**
     * Crea una nuova rotta
     * @param path il percorso da contattare per la richiesta
     * @param
     */
    public Route(String path, String controllerName, String method, int authLevel) {
            this.path       = path;
            this.controllerName = controllerName;
            this.methodName = method;
            this.authLevel = authLevel;
    }
}



