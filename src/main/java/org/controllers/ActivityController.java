package org.controllers;

import org.httpServer.http.HttpRequest;
import org.models.activities.Activity;
import org.services.ActivityService;

public class ActivityController extends Controller<Activity, ActivityService> {


    public  ActivityController(ActivityService service, HttpRequest request) {
        super(service, request);
    }

//    /**
//     * Gestisce i differenti endpoint per le request http di tipo POST
//     * @throws IOException
//     */
//    protected void handlePostCalls()throws IOException{
//        String[] admittedRoles = {"platformAdmin", "contributor","authContributor", "animator"};
//        if(this.httpRequestHandler.getCurrentUser().hasRole(admittedRoles))
//            super.create();
//        else{
//            HttpResponses.error(this.httpRequestHandler.getExchange(),401, "Non disponi dei permessi necessari" );
//        }
//    }
}
