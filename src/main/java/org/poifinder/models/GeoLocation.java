package org.poifinder.models;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import org.poifinder.dataMappers.Views;

@Entity
@Table(name="geolocations")
public class GeoLocation extends Content {

    @JsonView(Views.Public.class)
    private String address;

    @JsonView(Views.Public.class)
    private String number;

    @JsonView(Views.Public.class)
    private String cap;

    @JsonView(Views.Public.class)
    private double latitude;

    @JsonView(Views.Public.class)
    private double longitude;


    public GeoLocation() {  }

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



    /** getters **/
    public double getLatitude()  { return latitude; }
    public double getLongitude() { return longitude;}
    public String getAddress()   { return address;  }
    public String getNumber()    { return number;   }
    public String getCap()       { return cap;      }

    /** setters **/
    public void setLatitude(double latitude) {this.latitude = latitude; }
    public void setLongitude(double longitude) {this.longitude = longitude; }
    public void setAddress(String address) {this.address = address;}
    public void setNumber(String number)   {this.number = number;}
    public void setCap(String cap) {this.cap = cap;}
}
