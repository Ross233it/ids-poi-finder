package org.models.informations;

import org.models.Model;

public class GeoLocation extends Model implements Information{

    private int id;

    private double latitude;
    private double longitude;
    private String address;
    private String number;
    private String cap;

    public GeoLocation(
                       String address,
                       String number,
                       String cap) {
        this.address = address;
        this.number = number;
        this.cap = cap;
        this.latitude = 0.00;
        this.longitude = 0.00;
    }

    public GeoLocation(
                       String address,
                       String number,
                       String cap,
                       double latitude,
                       double longitude) {
        this.address = address;
        this.number = number;
        this.cap = cap;
        this.latitude = latitude;
        this.longitude = longitude;
    }



    public Object[]  getData(){ return new Object[] {
            this.address, this.number, this.cap, this.latitude, this.longitude };}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}
