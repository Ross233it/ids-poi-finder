package org.services;

import org.models.GeoLocation;
import org.repositories.GeoLocationRepository;


import java.util.Map;

public class GeoLocationService  extends Service<GeoLocation> {

    public GeoLocationService(GeoLocationRepository repository) {
        super(repository);
    }


    @Override
    protected GeoLocation buildEntity(Map<String, Object> geolocationData) {
        return new GeoLocation(
                (String) geolocationData.get("address"),
                (String) geolocationData.get("number"),
                (String) geolocationData.get("cap"),
                (double) geolocationData.get("latitude"),
                (double) geolocationData.get("longitude"));
    }
}
