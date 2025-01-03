package org.services;

import org.models.GeoLocation;
import org.repositories.GeoLocationRepository;


import java.util.Map;

public class GeoLocationService  extends Service<GeoLocation> {

    public GeoLocationService(GeoLocationRepository repository) {
        super(repository);
    }

    @Override
    public String index() {
        return "";
    }

    @Override
    public GeoLocation delete(long id) throws Exception {
        return null;
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
