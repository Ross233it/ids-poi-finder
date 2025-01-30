package org.httpServer;

import org.controllers.ActivityController;
import org.controllers.MunicipalityController;
import org.controllers.PoiController;
import org.controllers.RegisteredUserController;

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
                return new HttpUserRequestHandler(new RegisteredUserController());
            case "PoiController":
                return new HttpRequestHandler<PoiController>(new PoiController());
            case "MunicipalityController":
                return new HttpRequestHandler<MunicipalityController>(new MunicipalityController());
            case "ActivityController":
                return new HttpRequestHandler<ActivityController>(new ActivityController());
            default:
                return null;
        }
    }
}
