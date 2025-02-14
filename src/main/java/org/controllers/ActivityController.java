package org.controllers;

import org.httpServer.http.HttpRequest;
import org.models.activities.Activity;
import org.services.ActivityService;

public class ActivityController extends Controller<Activity, ActivityService> {


    public  ActivityController(ActivityService service, HttpRequest request) {
        super(service, request);
    }


    public Activity askForContest(){
        return null;
    }


    public Activity createActivity(){
        return null;
    }


    public Activity createItinerary(){
        return null;
    }


    public void deleteActivity(){

    }




}
