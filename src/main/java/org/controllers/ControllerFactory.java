package org.controllers;

import org.httpServer.http.HttpRequest;
import org.repositories.ActivityRepository;
import org.repositories.MunicipalityRepository;
import org.repositories.PoiRepository;
import org.repositories.RegisteredUserRepository;
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
                RegisteredUserRepository userRepository = new RegisteredUserRepository();
                RegisteredUserService userService = new RegisteredUserService(userRepository);
                return new RegisteredUserController(userService, request);

            case "PoiController":
                PoiRepository poiRepository = new PoiRepository("municipalities");
                PoiService poiService = new PoiService(poiRepository);
                return new PoiController(poiService, request);

            case "MunicipalityController":
                MunicipalityRepository munRepository = new MunicipalityRepository();
                MunicipalityService munService = new MunicipalityService(munRepository);
                return new MunicipalityController(munService, request);

            case "ActivityController":
                ActivityRepository actRepository = new ActivityRepository("activities");
                ActivityService actService = new ActivityService(actRepository);
                return new ActivityController(actService, request);
            default:
                return null;
        }
    }
}
