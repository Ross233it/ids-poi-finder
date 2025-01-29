package org.controllers;

import org.httpServer.HttpRequestHandler;
import org.models.users.RegisteredUser;
import org.services.RegisteredUserService;

public class HttpHandlerFactory {

    /**
     * Metodo per avviare la creazione di un controller
     * @param controllerName string nome del controller
     * @return
     */
    public HttpRequestHandler createHandler(String controllerName) {
        return getHandler(controllerName);
    }


    /**
     * Factory che crea un controller in base al nome passato come argomento
     * @param controllerName
     * @return
     */
    private HttpRequestHandler getHandler(String controllerName) {
        switch (controllerName) {
            case "RegisteredUserController":
                return new HttpRequestHandler(new RegisteredUserController());
            case "PoiController":
                return new HttpRequestHandler(new PoiController());
            case "MunicipalityController":
                return new HttpRequestHandler(new MunicipalityController());
            case "ActivityController":
                return new HttpRequestHandler(new ActivityController());
            default:
                return null;
        }
    }
}
