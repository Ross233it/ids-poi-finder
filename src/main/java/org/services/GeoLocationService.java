package org.services;

import org.models.informations.GeoLocation;
import org.repositories.GeoLocationRepository;


import java.util.Map;

public class GeoLocationService  implements Service<GeoLocation>{

    public GeoLocation create(Map<String, Object> data) {
        GeoLocation geoLocation  = new GeoLocation(
                (String)data.get("address"),
                (String)data.get("number"),
                (String)data.get("cap"),
                (double)data.get("latitude"),
                (double)data.get("longitude")
        );
        GeoLocationRepository geoLocationRepository = new GeoLocationRepository();
        GeoLocation dbGeoLocation = geoLocationRepository.create(geoLocation);
        return dbGeoLocation;
    }
}
