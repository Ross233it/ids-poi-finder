package org.services;

import org.models.informations.GeoLocation;
import org.repositories.GeoLocationRepository;


import java.util.Map;

public class GeoLocationService  implements IService<GeoLocation> {

    GeoLocationRepository repository;

    public GeoLocationService() {
        this.repository = new GeoLocationRepository("geolocations");
    }

    @Override
    public String index() {
        return "";
    }

    public  GeoLocation create(Map<String, Object> objectData){

        GeoLocation geoLoc = new GeoLocation(
                (String) objectData.get("address"),
                (String) objectData.get("number"),
                (String) objectData.get("cap"),
                (double) objectData.get("latitude"),
                (double) objectData.get("longitude")
        );
        try {
            geoLoc = this.repository.create(geoLoc);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println(objectData);
        return geoLoc;
    }

    @Override
    public  GeoLocation getObjectById(int id) {
        return null;
    }

    @Override
    public GeoLocation delete(int id) throws Exception {
        return null;
    }

}
