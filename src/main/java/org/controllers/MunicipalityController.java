package org.controllers;

import org.httpServer.HttpResponses;
import org.models.municipalities.Municipality;
import org.repositories.MunicipalityRepository;
import org.services.MunicipalityService;

import java.io.IOException;

public class MunicipalityController extends Controller<Municipality> {

    public  MunicipalityController() {
        super(new MunicipalityService(new MunicipalityRepository("municipalities")));
    }

    /**
     * Gestisce la richiesta http di visualizzazione di un municipio e dei suoi dettagli
     * @param id identificativo univoco della risorsa.
     * @throws IOException
     */
    @Override
    public void show(long id) throws IOException {
        try {
            Municipality item = (Municipality) this.service.getObjectById(id);
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

    /**
     * Gestisce i differenti endpoint per le request http di tipo POST
     * @throws IOException
     */
    @Override
    protected void handlePostCalls()throws IOException{
        String[] roles = {"platformAdmin","contributor","authContributor"};
        if(this.currentUser.hasRole(roles))
            this.create();
        else
            HttpResponses.error(this.exchange, 401, "Non autorizzato");
    }

}

