package org.services;


import org.models.Municipality;
import org.models.informations.GeoLocation;
import org.repositories.MunicipalityRepository;

import java.sql.SQLException;
import java.util.Map;

public class MunicipalityService implements Service<Municipality>{

    /**
     * Create a new Municipality via Builder
     * @return Municipality
     */
    public Municipality create(Map<String, Object> data) {

        String name = (String) data.get("name");

        GeoLocationService geoLocationService = new GeoLocationService();
        GeoLocation dbGeoLocation = geoLocationService.create((Map<String, Object>) data.get("geoLocation"));

        Municipality municipality = new Municipality(name, dbGeoLocation);

        MunicipalityRepository municipalityRepository = new MunicipalityRepository();
        try {
            municipalityRepository.create(municipality);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return municipality;
    }
}
