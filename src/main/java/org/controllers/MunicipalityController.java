package org.controllers;

import com.sun.net.httpserver.HttpHandler;
import org.models.Municipality;

import org.services.MunicipalityService;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.util.Map;

public class MunicipalityController extends ControllerFactory implements HttpHandler {

    public MunicipalityController(){
        super(new MunicipalityService());
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        switch (exchange.getRequestMethod()) {
            case "GET": this.index();   break;
            case "POST": this.create(exchange); break;
            case "PATCH": this.update(); break;
            case "DELETE": this.delete(); break;
            default: this.index();
        }
    }

    public void index(){}

    public void update(){}

    /**
     * Receive data from frontend and respond after creation.
     * @param exchange
     * @throws IOException
     */
    public void create(HttpExchange exchange) throws IOException {
        Map<String, Object> data = this.getStreamData(exchange);
        Municipality newMunicipality = (Municipality) this.service.create(data);
        if(null != newMunicipality) {
            this.positiveResponse(exchange, "Esito positivo.");
        }else
            this.errorResponse(exchange, "Esito positivo.",500);
    }

    private void delete(){
        System.out.print("Comune: Elimina comune: ");
    }
}

