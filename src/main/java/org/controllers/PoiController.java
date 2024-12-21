package org.controllers;


import org.models.poi.Poi;
import org.services.PoiService;

public class PoiController extends Controller<Poi> {

    public  PoiController() {
        super(new PoiService());
    }
}
