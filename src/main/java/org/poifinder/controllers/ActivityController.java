package org.poifinder.controllers;

import org.poifinder.models.activities.Activity;
import org.poifinder.services.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("api/activitiy")
public class ActivityController extends BaseController<Activity> {

    @Autowired
    public  ActivityController(ActivityService service) {
        super(service);
    }

    @PostMapping
    public ResponseEntity<Activity> create(@RequestBody Activity activity) throws IOException {
        return null;
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
