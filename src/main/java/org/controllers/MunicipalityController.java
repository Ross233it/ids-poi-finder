package org.controllers;

import com.sun.net.httpserver.HttpHandler;
import org.services.MunicipalityService;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;

public class MunicipalityController extends ControllerFactory  implements HttpHandler {
    String requestPath;

    public MunicipalityController(){
        super(new MunicipalityService());
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        super.handle(exchange);
    }

    public void index(){}

    public void update(){}

    /**
     * Receive data from frontend and respond after creation.
     * @param exchange
     * @throws IOException
     */
    @Override
    public void create() throws IOException{
//        if(this.requestPath.equals("/api/municipality")){
//            Map<String, Object> data = this.getStreamData(exchange);
//            System.out.println("metodo raggiunto");
//
//            Municipality newMunicipality = (Municipality) this.service.create(data);
//            if(null != newMunicipality) {
//                this.responses.success(exchange, "Esito positivo.");
//            }else
//                this.responses.error(exchange, 500,"Esito Negativissimo.");
//        }else
//            this.responses.error(exchange, 404, "Resource not found 404");
    }

    @Override
    public void delete(){
        System.out.print("Comune: Elimina comune: ");
    }

//    @Override
//    protected void index(HttpExchange exchange) throws IOException {}
}

