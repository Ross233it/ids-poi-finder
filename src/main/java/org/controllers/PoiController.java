package org.controllers;

import org.httpServer.HttpResponses;
import org.models.poi.Poi;
import org.models.users.RegisteredUser;
import org.repositories.PoiRepository;
import org.services.PoiService;
import org.services.RegisteredUserService;

import java.io.IOException;
import java.util.Map;

public class PoiController extends Controller<Poi> {

    public  PoiController() {
        super(new PoiService(new PoiRepository("pois")));
    }

    @Override
    public void show(long id) throws IOException {
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

    /**
     * Gestisce la richiesta di validazione di un contenuto
     * @throws Exception
     */
    public void setStatus() throws Exception{
        if(this.currentUser.hasRole("platformAdmin")){
            try {
                Map<String, Object> data = HttpResponses.getStreamData(this.exchange);
                if(data.get("status") == null || data.get("id") == null)
                    HttpResponses.error(this.exchange, 404, "Dati mancanti");
                Poi poi = ((PoiService) this.service).setStatus(data);
                if(poi == null)
                    HttpResponses.error(this.exchange, 404, "Modifica fallita");
                else
                    HttpResponses.success(this.exchange, HttpResponses.objectToJson(poi));
            } catch (Exception e) {
                HttpResponses.error(this.exchange, 500, e.getMessage());
            }
        } else
            HttpResponses.error(this.exchange,401, "Non Autorizzato" );
    }
}
