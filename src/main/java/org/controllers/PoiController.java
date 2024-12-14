package org.controllers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.models.poi.PhisicalPoi;
import org.models.poi.Poi;
import org.services.PoiService;


import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

public class PoiController extends ControllerFactory implements HttpHandler/*extends ControllerFactory*/{

    public  PoiController() {
        super(new PoiService());
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        super.handle(exchange);
    }

    @Override
    public void create() throws IOException {
        this.responses.success(this.exchange, "io sono create");
//        if(this.requestPath.equals("/api/poi")){
//            Map<String, Object> data = this.getStreamData(exchange);
//
//            Poi newPoi = (PhisicalPoi) this.service.create(data);
//            if(null != newPoi) {
//                this.responses.success(exchange, "Esito positivo.");
//            }else
//                this.responses.error(exchange, 500, "Esito Negativissimo.");
//        }else
//            this.responses.error(exchange, 404,"Resource not found 404");

    }

    @Override
    protected void index() throws IOException {
        this.responses.success(this.exchange, "io sono index");
    }


    @Override
    protected void update() throws IOException {
        this.responses.success(this.exchange, "io sono update");
    }

    @Override
    protected void delete() throws IOException {
        this.responses.success(this.exchange, "io sono Delete");
    }

}
