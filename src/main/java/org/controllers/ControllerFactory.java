package org.controllers;

import org.httpServer.http.HttpRequest;
import org.services.ActivityService;
import org.services.MunicipalityService;
import org.services.PoiService;
import org.services.RegisteredUserService;

public class ControllerFactory {

    /**
     * Metodo per avviare la creazione di un controller
     * @param controllerName
     * @return
     */
    public Controller createController(String controllerName, HttpRequest request) {
        return getController(controllerName, request);
    }

    /**
     * Factory che crea un controller in base al nome passato come argomento
     * @param controllerName
     * @return
     */
    private Controller getController(String controllerName, HttpRequest request) {
        switch (controllerName) {
            case "RegisteredUserController":
                RegisteredUserService userService = new RegisteredUserService();
                return new RegisteredUserController(userService, request);

            case "PoiController":
                PoiService poiService = new PoiService();
                return new PoiController(poiService, request);

            case "MunicipalityController":
                MunicipalityService munService = new MunicipalityService();
                return new MunicipalityController(munService, request);

            case "ActivityController":
                ActivityService actService = new ActivityService();
                return new ActivityController(actService, request);
            default:
                return null;
        }
    }
}
