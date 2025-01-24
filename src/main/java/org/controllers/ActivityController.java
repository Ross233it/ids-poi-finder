package org.controllers;

import org.models.poi.Poi;
import org.repositories.ActivityRepository;
import org.services.ActivityService;

public class ActivityController extends Controller<Poi> {

    public  ActivityController() {
        super(new ActivityService(new ActivityRepository("activities")));
    }




}
