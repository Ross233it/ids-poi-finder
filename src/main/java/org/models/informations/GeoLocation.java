package org.models.informations;

public class GeoLocation implements Information{

    private long id;

    private double latitude;
    private double longitude;
    private String address;
    private String number;
    private String cap;

    public GeoLocation(long id,
                       String address,
                       String number,
                       String cap) {

        this.id = id;
        this.address = address;
        this.number = number;
        this.cap = cap;
        this.latitude = 0.00;
        this.longitude = 0.00;
    }

    public GeoLocation(long id,
                       String address,
                       String number,
                       String cap,
                       double latitude,
                       double longitude) {

        this.id = id;
        this.address = address;
        this.number = number;
        this.cap = cap;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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
