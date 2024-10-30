package org.controllers;

import org.models.Municipality;
import org.services.MunicipalityService;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.util.Map;

public class MunicipalityController extends ControllerFactory  {
    String requestPath;

    public MunicipalityController(){
        super(new MunicipalityService());
    }

    public void index(){}

    public void update(){}

    /**
     * Receive data from frontend and respond after creation.
     * @param exchange
     * @throws IOException
     */
    @Override
    public void create(HttpExchange exchange) throws IOException {
        if(this.requestPath.equals("/api/municipality")){
            Map<String, Object> data = this.getStreamData(exchange);

            Municipality newMunicipality = (Municipality) this.service.create(data);
            if(null != newMunicipality) {
                this.positiveResponse(exchange, "Esito positivo.");
            }else
                this.errorResponse(exchange, "Esito Negativissimo.",500);
        }else
            this.errorResponse(exchange, "Resource not found 404",404);
    }

    @Override
    public void delete(){
        System.out.print("Comune: Elimina comune: ");
    }

    @Override
    protected void index(HttpExchange exchange) throws IOException {}
}

