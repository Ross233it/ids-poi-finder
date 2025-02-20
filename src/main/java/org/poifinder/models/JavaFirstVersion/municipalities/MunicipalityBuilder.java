package org.poifinder.models.JavaFirstVersion.municipalities;

import org.poifinder.models.JavaFirstVersion.GeoLocation;
import org.poifinder.models.JavaFirstVersion.poi.Poi;
import org.poifinder.models.JavaFirstVersion.users.RegisteredUser;

import java.util.ArrayList;


public class MunicipalityBuilder {
    private String name;
    private String region;
    private String province;
    private String status;

    private GeoLocation geoLocation;

    private RegisteredUser author;

    private RegisteredUser approver;

    private ArrayList<Poi> pois;

    public MunicipalityBuilder(String name, String region,String province) {
        this.name = name;
        this.region = region;
        this.province = province;
        this.pois = new ArrayList<Poi>();
    }

    public MunicipalityBuilder geoLocation(GeoLocation geoLocation) {
        this.geoLocation = geoLocation;
        return this;
    }

    public MunicipalityBuilder status(String status) {
        this.status = status;
        return this;
    }

    public MunicipalityBuilder author(RegisteredUser author) {
        this.author = author;
        return this;
    }

    public MunicipalityBuilder approver(RegisteredUser approver) {
        this.approver = approver;
        return this;
    }

    public MunicipalityBuilder pois(ArrayList<Poi> pois) {
        this.pois = pois;
        return this;
    }

    public Municipality build() {
        return new Municipality(this);
    }

    /*** getters ***/

    public String           getName()        { return name; }
    public String           getRegion()      { return region; }
    public String           getProvince()    { return province; }
    public String           getStatus()      { return status; }
    public GeoLocation getGeoLocation() { return geoLocation; }
    public RegisteredUser getAuthor()      { return author; }
    public RegisteredUser getApprover()    { return approver; }
    public ArrayList<Poi>   getPois()        { return pois; }
}

