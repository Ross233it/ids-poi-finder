package org.controllers;

import org.httpServer.HttpResponses;
import org.models.activities.Activity;
import org.models.poi.Poi;
import org.repositories.ActivityRepository;
import org.services.ActivityService;

import java.io.IOException;

public class ActivityController extends Controller<Activity, ActivityService> {

    public  ActivityController() {
        super(new ActivityService(new ActivityRepository("activities")));
    }

    /**
     * Gestisce i differenti endpoint per le request http di tipo POST
     * @throws IOException
     */
    protected void handlePostCalls()throws IOException{
        String[] admittedRoles = {"platformAdmin", "contributor","authContributor", "animator"};
        if(this.currentUser.hasRole(admittedRoles))
            super.create();
        else{
            HttpResponses.error(this.exchange,401, "Non disponi dei permessi necessari" );
        }
    }
}
