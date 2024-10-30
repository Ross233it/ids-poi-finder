package org.models.poi;

import org.models.Municipality;
import org.models.informations.GeoLocation;
import org.models.informations.PoiDetail;
import org.models.informations.PoiType;

import java.util.ArrayList;
import java.util.List;

public class PhisicalPoi implements Poi {
    private long id;

    private String name;

    private String description;

    private Boolean isLogical = false;

    private Municipality municipality;

    private GeoLocation geoLocation;

    private ArrayList<Tags> Tags;

    private ArrayList<Category> Categories;




    public PhisicalPoi(String name,
                       int municipality_id,
                       boolean isLogical,
                       PoiDetail poiDetails){
        this.name = name;
        this.municipalityId = municipality_id;
        this.isLogical  = isLogical;
        this.poiDetails = poiDetails;
    }

    public int getMunicipalityId() {
        return municipalityId;
    }

    public void setMunicipalityId(int municipalityId) {
        this.municipalityId = municipalityId;
    }

    public void setPoiDetails(PoiDetail poiDetails) {
        this.poiDetails = poiDetails;
    }

    public Boolean getLogical() {
        return isLogical;
    }

    public void setLogical(Boolean logical) {
        isLogical = logical;
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
