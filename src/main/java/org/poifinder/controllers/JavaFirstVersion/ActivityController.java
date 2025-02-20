package org.poifinder.controllers.JavaFirstVersion;

import org.poifinder.httpServer.http.HttpRequest;
import org.poifinder.models.activities.Activity;
import org.poifinder.services.ActivityService;

import java.io.IOException;

//@RestController
//@RequestMapping("api/activitiy")
public class ActivityController extends Controller<Activity, ActivityService> {

//    @Autowired
    public  ActivityController(ActivityService service, HttpRequest request) {
        super(service, request);
    }

//    @PostMapping
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
