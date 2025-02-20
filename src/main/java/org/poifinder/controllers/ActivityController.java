package org.poifinder.controllers;

import org.poifinder.models.activities.Activity;
import org.poifinder.services.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("api/activitiy")
public class ActivityController extends BaseController<Activity, ActivityService> {

    @Autowired
    public  ActivityController(ActivityService service) {
        super(service);
    }

    @PostMapping
    public void create( Activity activity) throws IOException {

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
