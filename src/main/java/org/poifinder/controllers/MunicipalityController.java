package org.poifinder.controllers;

import org.poifinder.models.municipalities.Municipality;
import org.poifinder.services.MunicipalityService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("api/municipality")
public class MunicipalityController extends BaseController<Municipality, MunicipalityService> {

    public  MunicipalityController(MunicipalityService service) {
        super(service);
    }


    /**
     * Gestisce le richieste di visualizzazione del comune con
     * tutti i poi a lui correlati
     * @throws IOException
     */
    public void getWithPois() throws IOException {
//        long municipalityId = request.getRequestId();
//        handleRequest(()-> service.getWithPois(municipalityId), null);
    }
}

