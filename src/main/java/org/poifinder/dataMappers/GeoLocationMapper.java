package org.poifinder.dataMappers;

import org.poifinder.models.GeoLocation;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class GeoLocationMapper extends DataMapper<GeoLocation> {

    private Long  id;

    private String address;

    private String number;

    private String cap;

    private double latitude;

    private double longitude;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCap() {
        return cap;
    }

    public void setCap(String cap) {
        this.cap = cap;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

}
