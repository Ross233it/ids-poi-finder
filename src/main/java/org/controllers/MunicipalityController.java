package org.controllers;

import com.sun.net.httpserver.HttpExchange;
import org.httpServer.HttpResponses;
import org.models.municipalities.Municipality;
import org.models.poi.Poi;
import org.services.MunicipalityService;

import java.io.IOException;
import java.util.ArrayList;

public class MunicipalityController extends Controller<Municipality, MunicipalityService> {

    public  MunicipalityController(MunicipalityService service, HttpExchange exchange) {
        super(service, exchange);
    }
    /**
     * Gestisce la richiesta http di visualizzazione di un municipio e dei suoi dettagli
     * @param id identificativo univoco della risorsa.
     * @throws IOException
     */
    @Override
    public void show(long id) throws IOException {
       
        try {
            Municipality item =  this.service.getObjectById(id);
            if(item == null)
                HttpResponses.error(this.exchange, 404, "Record non trovato");
            else{
                String municipalityData = HttpResponses.objectToJson(item);
                String geolocationData  = HttpResponses.objectToJson(item.getGeoLocation());
                String combinedJson = "{" + municipalityData + ", \"geoLocation\": " + geolocationData + "}";
                HttpResponses.success(this.exchange, combinedJson);
            }
        } catch (Exception e) {
            HttpResponses.error(this.exchange, 500, e.getMessage());
            }
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

