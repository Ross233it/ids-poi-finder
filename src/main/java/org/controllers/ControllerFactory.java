package org.controllers;

public class ControllerFactory {

    /**
     * Metodo per avviare la creazione di un controller
     * @param controllerName
     * @return
     */
    public Controller createController(String controllerName) {
        return getController(controllerName);
    }


    /**
     * Factory che crea un controller in base al nome passato come argomento
     * @param controllerName
     * @return
     */
    private Controller getController(String controllerName) {
        switch (controllerName) {
            case "UserController":
                return new UserController();
            case "PoiController":
                return new PoiController();
            case "MunicipalityController":
                return new MunicipalityController();
            default:
                return null;
        }
    }
}
