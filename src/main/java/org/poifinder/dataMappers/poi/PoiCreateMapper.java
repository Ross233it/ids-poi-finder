package org.poifinder.dataMappers.poi;

import org.poifinder.dataMappers.DataMapper;
import org.poifinder.models.poi.Poi;

public class PoiCreateMapper extends DataMapper<Poi> {

    private String name;

    private String description;

    private String type;

    private String address;

    private String number;

    private String cap;

    private boolean logical;

    private double latitude;

    private double longitude;

    private long municipality_id;


    /** getters **/

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
    }

    public boolean isLogical() {
        return logical;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }



    public long getMunicipality_id() {
        return municipality_id;
    }

    public String getAddress() {
        return address;
    }

    public String getNumber() {
        return number;
    }

    public String getCap() {
        return cap;
    }

    /** setters **/

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }



    /** setters **/

    public void setType(String type) {
        this.type = type;
    }

    public void setLogical(boolean logical) {
        this.logical = logical;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setMunicipality_id(long municipality_id) {
        this.municipality_id = municipality_id;
    }
}
