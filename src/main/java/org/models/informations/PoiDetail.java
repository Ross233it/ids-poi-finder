package org.models.informations;

import org.models.Municipality;

import java.util.List;

public class PoiDetail implements Information{
    private long id;

    private String description;

    private List<String> tags;

    private PoiType poyType;

    private Municipality municipality;

    private GeoLocation geoLocation;


    public PoiDetail() {
        this.description = null;
        this.tags    = null;
        this.poyType = null;
        this.municipality = null;
        this.geoLocation  = null;
    }

    public PoiDetail(String description,
                     List<String> tags,
                     PoiType poyType,
                     Municipality municipality,
                     GeoLocation geoLocation) {
        this.description = description;
        this.tags    = tags;
        this.poyType = poyType;
        this.municipality = municipality;
        this.geoLocation = geoLocation;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public PoiType getPoyType() {
        return poyType;
    }

    public void setPoyType(PoiType poyType) {
        this.poyType = poyType;
    }

    public Municipality getMunicipality() {
        return municipality;
    }

    public void setMunicipality(Municipality municipality) {
        this.municipality = municipality;
    }

    public GeoLocation getGeoLocation() {
        return geoLocation;
    }

    public void setGeoLocation(GeoLocation geoLocation) {
        this.geoLocation = geoLocation;
    }
}
