package org.controllers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.models.poi.BasePoi;
import org.models.poi.Poi;
import org.services.PoiService;


import java.io.IOException;
import java.util.Map;

public class PoiController extends BaseController implements HttpHandler{

    public  PoiController() {
        super(new PoiService());
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        super.handle(exchange);
    }

    @Override
    public void create() throws IOException {
        try{
            Map<String, Object> data = this.getStreamData(this.exchange);
            //todo validate data
//            if (name == null || description == null || address == null) {
//                throw new IllegalArgumentException("Geolocation è obbligatoria e deve essere completa.");
//            }

            Poi newPoi = (BasePoi) this.service.create(data);

            if(null != newPoi) {
                this.responses.success(this.exchange, "Poi creato con successo");
            }else
                this.responses.error(this.exchange, 500, "Esito Negativissimo.");
        }catch (Exception e) {
            this.responses.error(this.exchange, 500, e.getMessage());
        }
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
