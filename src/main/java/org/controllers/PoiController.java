package org.controllers;

import org.httpServer.http.HttpRequest;
import org.httpServer.http.HttpResponses;
import org.models.poi.Poi;
import org.services.PoiService;

import java.io.IOException;
import java.util.Map;

public class PoiController extends Controller<Poi, PoiService> {

    public  PoiController(PoiService service, HttpRequest request) {
        super(service, request);
    }

    /**
     * Gestisce la richiesta http di visualizzazione di un poi e dei suoi dettagli
     * @throws IOException
     */
//    @Override
//    public void show() throws IOException {
//        if(this.request.getRequestId() > 0){
//            long id = this.request.getRequestId();
//            try {
//                PoiService poiService= (PoiService) this.service;
//                Poi item = poiService.getObjectById(id);
//                if(item == null)
//                    HttpResponses.error(this.exchange, 404, "Record non trovato");
//                else{
//                    String combinedJson = poiService.printPoi(item);
//                    HttpResponses.success(this.exchange, combinedJson);
//                }
//            } catch (Exception e) {
//                HttpResponses.error(this.exchange, 500, e.getMessage());
//            }
//        }
//        HttpResponses.error(this.exchange, 500, "Parametro id mancante");
//    }

    /**
     * Gestisce la richiesta di validazione di un contenuto
     * @throws Exception
     */
    public void setStatus() throws Exception{
            try {
                Map<String, Object> data = request.getBodyStreamData();
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
    }

}
