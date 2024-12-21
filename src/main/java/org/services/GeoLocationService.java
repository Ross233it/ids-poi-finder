package org.services;

import org.models.informations.GeoLocation;
import org.repositories.GeoLocationRepository;


import java.util.Map;

public class GeoLocationService  implements Service<GeoLocation>{

    GeoLocationRepository repository;

    public GeoLocationService() {
        this.repository = new GeoLocationRepository("geolocations");
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

//    public GeoLocation create(Map<String, Object> data) {
//        GeoLocation geoLocation  = new GeoLocation(
//                (String)data.get("address"),
//                (String)data.get("number"),
//                (String)data.get("cap"),
//                (double)data.get("latitude"),
//                (double)data.get("longitude")
//        );
//        GeoLocationRepository geoLocationRepository = new GeoLocationRepository();
//        GeoLocation dbGeoLocation = geoLocationRepository.create(geoLocation);
//        return dbGeoLocation;
//    }
}
