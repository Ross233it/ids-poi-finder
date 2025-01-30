package org.httpServer;

import org.controllers.MunicipalityController;

import java.io.IOException;

public class HttpMunicipalityRequestHandler extends  HttpRequestHandler<MunicipalityController>{

    public HttpMunicipalityRequestHandler(MunicipalityController controller) {
        super(controller);
    }

    /**
     * Gestisce i differenti endpoint per le request http di tipo POST
    */
    @Override
    protected void handleGetCalls() throws IOException {
        if (this.requestPath.equals("/api/municipality/pois")){
            //todo rendere l'id dinamico
            long id = 5;
            if(id > 0){
                this.controller.getWithPois(id);
            }
        }
        else
            super.handleGetCalls();
    }

    @Override
    protected void handlePostCalls()throws IOException{
        String[] roles = {"platformAdmin","contributor","authContributor"};
        if(this.currentUser.hasRole(roles))
            this.controller.create();
        else
            HttpResponses.error(this.exchange, 401, "Non autorizzato");
    }
}
