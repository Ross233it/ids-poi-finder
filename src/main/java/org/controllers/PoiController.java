package org.controllers;

import com.sun.net.httpserver.HttpExchange;
import org.httpServer.HttpResponses;
import org.models.poi.Poi;
import org.services.PoiService;

import java.io.IOException;
import java.util.Map;

public class PoiController extends Controller<Poi, PoiService> {

    public  PoiController(PoiService service, HttpExchange exchange) {
        super(service, exchange);
    }


    /**
     * Gestisce la richiesta http di visualizzazione di un poi e dei suoi dettagli
     * @param id identificativo univoco della risorsa.
     * @throws IOException
     */
    @Override
    public void show(long id) throws IOException {
        try {
            PoiService poiService= (PoiService) this.service;
            Poi item = poiService.getObjectById(id);
            if(item == null)
                HttpResponses.error(this.exchange, 404, "Record non trovato");
            else{
                String combinedJson = poiService.printPoi(item);
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
    }

    /**
     * Gestisce i differenti endpoint per le request http di tipo POST
     * @throws IOException
     */

//    @Override
//    protected void handlePostCalls()throws IOException{
//        String[] roles = {"platformAdmin","contributor","authContributor"};
//        if(this.currentUser.hasRole(roles))
//            this.create();
//        else
//            HttpResponses.error(this.exchange, 401, "Non autorizzato");
//    }
}
