package org.controllers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.models.poi.BasePoi;
import org.models.poi.Poi;
import org.services.PoiService;


import java.io.IOException;
import java.util.Map;

public class PoiController extends BaseController<BasePoi> implements HttpHandler{

    public  PoiController() {
        super(new PoiService());
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        super.handle(exchange);
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
