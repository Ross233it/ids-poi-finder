package org.poifinder.controllers;


import org.poifinder.httpServer.http.HttpRequest;
import org.poifinder.models.municipalities.Municipality;
import org.poifinder.services.MunicipalityService;

import java.io.IOException;


public class MunicipalityController extends Controller<Municipality, MunicipalityService> {

    public  MunicipalityController(MunicipalityService service, HttpRequest request) {
        super(service, request);
    }


    /**
     * Gestisce le richieste di visualizzazione del comune con
     * tutti i poi a lui correlati
     * @throws IOException
     */
    public void getWithPois() throws IOException {
        long municipalityId = request.getRequestId();
        handleRequest(()-> service.getWithPois(municipalityId), null);
    }
}

