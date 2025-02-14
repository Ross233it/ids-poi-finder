package org.controllers;

import org.httpServer.http.HttpRequest;
import org.models.poi.Poi;
import org.models.users.RegisteredUser;
import org.services.PoiService;

import java.io.IOException;
import java.util.Map;

public class PoiController extends Controller<Poi, PoiService> {

    public  PoiController(PoiService service, HttpRequest request) {
        super(service, request);
    }

    @Override
    public void create() throws IOException {
        Map<String, Object> data = request.getBodyStreamData();
        RegisteredUser author = this.request.getCurrentUser();
        data.put("author", author);
        if(
            author.hasRole("platformAdmin") ||
            author.hasRole("animator") ||
            author.hasRole("authContributor")
        ){
            data.put("status", "published");
        }
        handleRequest(()->service.create(data), null);
    }

    /**
     * Gestisce la richiesta di tutti i poi di un municipio
     * @throws IOException
     */
    public void getByMunicipalityId() throws IOException {
        long municipalityId = request.getRequestId();
        handleRequest(()-> service.getByMunicipalityId(municipalityId), null);
    }

}
