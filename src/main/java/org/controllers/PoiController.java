package org.controllers;

import com.sun.net.httpserver.HttpExchange;
import org.services.MunicipalityService;

import java.io.IOException;

public class PoiController extends ControllerFactory{

    public PoiController(){
        super(new PoiService());
    }


    @Override
    public void create(HttpExchange exchange) {
        Integer municipalityId = this.extractIdFromUrl(this.requestPath, "^/municipality/(\\d+)/poi$",4,2);
        if(municipalityId != null){



        }
    }

    @Override
    protected void index() {
    }


    @Override
    protected void update() throws IOException {

    }

    @Override
    protected void delete() throws IOException {

    }

    @Override
    protected void index(HttpExchange exchange) throws IOException {

    }
}
