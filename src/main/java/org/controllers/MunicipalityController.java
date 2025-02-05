package org.controllers;

import org.httpServer.http.HttpRequest;
import org.httpServer.http.HttpResponses;
import org.models.municipalities.Municipality;
import org.models.poi.Poi;
import org.services.MunicipalityService;

import java.io.IOException;
import java.util.ArrayList;

public class MunicipalityController extends Controller<Municipality, MunicipalityService> {

    public  MunicipalityController(MunicipalityService service, HttpRequest request) {
        super(service, request);
    }


    public void getWithPois(long id) throws IOException {
        try {
            Municipality item = this.service.getWithPois(id);
            ArrayList<Poi> pois = item.getPois();

            if(item == null)
                HttpResponses.error(this.exchange, 404, "Record non trovato");
            else{
                String municipalityData = HttpResponses.objectToJson(item);
                String poiJsonData = "";
                int i = 0;
                for(Poi poi : pois){
                    String poiData = HttpResponses.objectToJson(poi);
                    poiJsonData += " \""+ i +"\": " + poiData + ",\n";
                    i++;
                }
                String combinedJson = "{" + municipalityData + ", \"pois\": [ " + poiJsonData + "]}";

                HttpResponses.success(this.exchange, combinedJson);
            }
        } catch (Exception e) {
            HttpResponses.error(this.exchange, 500, e.getMessage());
        }
    }
}

