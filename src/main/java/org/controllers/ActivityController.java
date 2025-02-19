package org.controllers;

import org.httpServer.http.HttpRequest;
import org.models.activities.Activity;
import org.services.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("api/activitiy")
public class ActivityController extends Controller<Activity, ActivityService> {

    @Autowired
    public  ActivityController(ActivityService service, HttpRequest request) {
        super(service, request);
    }

    @PostMapping
    public void create(@RequestBody Activity activity) throws IOException {

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
