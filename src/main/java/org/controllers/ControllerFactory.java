package org.controllers;

import com.sun.net.httpserver.HttpExchange;
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
    public Controller createController(String controllerName, HttpExchange exchange) {
        return getController(controllerName, exchange);
    }


    /**
     * Factory che crea un controller in base al nome passato come argomento
     * @param controllerName
     * @return
     */
    private Controller getController(String controllerName, HttpExchange exchange) {
        switch (controllerName) {
            case "RegisteredUserController":

                RegisteredUserRepository userRepository = new RegisteredUserRepository("users");
                RegisteredUserService userService = new RegisteredUserService(userRepository);
                return new RegisteredUserController(userService, exchange);

            case "PoiController":
                PoiRepository poiRepository = new PoiRepository("municipalities");
                PoiService poiService = new PoiService(poiRepository);
                return new PoiController(poiService, exchange);

            case "MunicipalityController":
                MunicipalityRepository munRepository = new MunicipalityRepository("municipalities");
                MunicipalityService munService = new MunicipalityService(munRepository);
                return new MunicipalityController(munService, exchange);

            case "ActivityController":
                ActivityRepository actRepository = new ActivityRepository("activities");
                ActivityService actService = new ActivityService(actRepository);
                return new ActivityController(actService, exchange);
            default:
                return null;
        }
    }
}
