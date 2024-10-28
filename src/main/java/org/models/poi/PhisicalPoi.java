package org.models.poi;

import org.models.informations.PoiDetail;

public class PhisicalPoi implements Poi {
    private long id;

    private String name;

    private PoiDetail poiDetails;

    private Boolean logical_poi = false;

    public PhisicalPoi(String name, PoiDetail poiDetails) {
        this.name = name;
        this.poiDetails = poiDetails;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public PoiDetail getPoiDetails() {
        return poiDetails;
    }

    @Override
    public void setPoiDetails(PoiDetail poiDetails, Boolean logical_poi) {
        this.poiDetails = poiDetails;
    }
}
