package org.controllers;

import org.httpServer.HttpResponses;
import org.models.poi.Poi;
import org.repositories.PoiRepository;
import org.services.PoiService;

import java.io.IOException;

public class PoiController extends Controller<Poi> {

    public  PoiController() {
        super(new PoiService(new PoiRepository("pois")));
    }

    @Override
    public void show(int id) throws IOException {
        try {
            Poi item = (Poi) this.service.getObjectById(id);
            if(item == null)
                HttpResponses.error(this.exchange, 404, "Record non trovato");
            else{
                String poiData = HttpResponses.objectToJson(item);
                String geolocationData  =  HttpResponses.objectToJson(item.getGeoLocation());
                String municipalityData  = HttpResponses.objectToJson(item.getMunicipality());
                String combinedJson = "{"
                        + poiData
                        + ", \"geoLocation\": "  + geolocationData
                        + ", \"municipality\": " + municipalityData
                        +"}";
                HttpResponses.success(this.exchange, combinedJson);
            }
        } catch (Exception e) {
            HttpResponses.error(this.exchange, 500, e.getMessage());
        }
    }
}
