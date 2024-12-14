package org.services;


import org.models.Municipality;
import org.models.informations.GeoLocation;
import org.repositories.MunicipalityRepository;
import org.repositories.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class MunicipalityService implements Service<Municipality>{


    private final MunicipalityRepository repository;


    public MunicipalityService() {
        this.repository = new MunicipalityRepository();
    }
    /**
     * Create a new Municipality via Builder
     * @return Municipality
     */
    public Municipality create(Map<String, Object> data) {
        String name = (String) data.get("name");

        GeoLocationService geoLocationService = new GeoLocationService();
        GeoLocation dbGeoLocation = geoLocationService.create((Map<String, Object>) data.get("geoLocation"));

        Municipality municipality = new Municipality(name, dbGeoLocation);

        try {
            repository.create(municipality);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return municipality;
    }

    public ResultSet getById(Integer id)throws SQLException {
         return this.repository.getById(id);
    }

}
