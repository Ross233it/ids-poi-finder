package org.models;

import org.models.informations.GeoLocation;
import org.models.poi.Poi;

import java.util.ArrayList;

public class Municipality {
    private String name;

    private GeoLocation geoLocation;

    private ArrayList<Poi> pois;

    public Municipality(String name, GeoLocation geoLocation) {
        this.name = name;
        this.geoLocation = geoLocation;
        this.pois = new ArrayList<Poi>();
    }

    public Municipality(String name, GeoLocation geoLocation, ArrayList<Poi> pois) {
        this.name = name;
        this.geoLocation = geoLocation;
        this.pois = pois;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GeoLocation getGeoLocation() {
        return geoLocation;
    }

    public void setGeoLocation(GeoLocation geoLocation) {
        this.geoLocation = geoLocation;
    }


}
