package org.services;

import org.models.informations.GeoLocation;
import org.repositories.GeoLocationRepository;
import org.repositories.MunicipalityRepository;


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
    public GeoLocation delete(int id) throws Exception {
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
