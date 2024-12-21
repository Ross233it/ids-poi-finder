package org.models;

import org.models.informations.GeoLocation;
import org.models.poi.IPoi;

import java.util.ArrayList;

public class Municipality {
    private int id;

    private String name;

    private GeoLocation geoLocation;

    private ArrayList<IPoi> pois;

    public Municipality(String name){
        this.name = name;
    };

    public Municipality(String name, GeoLocation geoLocation) {
        this.name = name;
        this.geoLocation = geoLocation;
    }


    public Municipality(String name, GeoLocation geoLocation, ArrayList<IPoi> pois) {
        this.name = name;
        this.geoLocation = geoLocation;
        this.pois = pois;
    }



    public Object[]  getData(){ return new Object[] { this.name };}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GeoLocation getGeoLocation() {
        return this.geoLocation;
    }

    public void setGeoLocation(GeoLocation geoLocation) {
        this.geoLocation = geoLocation;
    }


}
