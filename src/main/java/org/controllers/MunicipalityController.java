package org.controllers;

import com.sun.net.httpserver.HttpHandler;
import org.models.Municipality;
import org.services.MunicipalityService;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.util.Map;

public class MunicipalityController extends BaseController implements HttpHandler{


    public  MunicipalityController() {

        super(new MunicipalityService());
        //todo remove
        System.out.println("costruttore");
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        super.handle(exchange);
    }

    @Override
    protected void index() throws IOException {

    }

    @Override
    protected void create() throws IOException {
        try{
            Map<String, Object> data = this.getStreamData(this.exchange);
            //todo validate data
//            if (name == null || description == null || address == null) {
//                throw new IllegalArgumentException("Geolocation è obbligatoria e deve essere completa.");
//            }
            //todo remove
            System.out.println("controller raggiunto");
            Municipality newMunicipality = (Municipality) this.service.create(data);

            if(null != newMunicipality) {
                this.responses.success(this.exchange, "Poi creato con successo");
            }else
                this.responses.error(this.exchange, 500, "Si è verificato un problema nella creazione del record");
        }catch (Exception e) {
            this.responses.error(this.exchange, 500, e.getMessage());
        }
    }

    @Override
    protected void update() throws IOException {

    }

    @Override
    protected void delete() throws IOException {

    }


}

