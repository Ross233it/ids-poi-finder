package org.models.municipalities;

import org.models.GeoLocation;
import org.models.poi.IPoi;
import org.models.users.RegisteredUser;

import java.util.ArrayList;


public class MunicipalityBuilder {
    private String name;
    private String region;
    private String province;

    private GeoLocation geoLocation;

    private RegisteredUser author;

    private ArrayList<IPoi> pois;

    public MunicipalityBuilder(String name, String region,String province) {
        this.name = name;
        this.region = region;
        this.province = province;
    }

    public MunicipalityBuilder geoLocation(GeoLocation geoLocation) {
        this.geoLocation = geoLocation;
        return this;
    }

    public MunicipalityBuilder author(RegisteredUser author) {
        this.author = author;
        return this;
    }

    public MunicipalityBuilder pois(ArrayList<IPoi> pois) {
        this.pois = pois;
        return this;
    }

    public Municipality build() {
        return new Municipality(this);
    }
}

