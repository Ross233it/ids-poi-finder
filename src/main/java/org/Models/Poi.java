package org.Models;

public class Poi{
    private long id;

    private String name;

    private PoiDetail poiDetails;


    public Poi(String name, PoiDetail poiDetails) {
        this.name = name;
        this.poiDetails = poiDetails;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PoiDetail getPoiDetails() {
        return poiDetails;
    }

    public void setPoiDetails(PoiDetail poiDetails) {
        this.poiDetails = poiDetails;
    }
}
