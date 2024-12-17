package org.services;


import org.models.Municipality;
import org.models.informations.GeoLocation;
import org.models.poi.BasePoi;
import org.models.poi.Poi;
import org.models.poi.PoiBuilder;
import org.repositories.MunicipalityRepository;
import org.repositories.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class MunicipalityService implements Service<Municipality>{


    private final MunicipalityRepository repository;


    public MunicipalityService() {
        this.repository = new MunicipalityRepository("municipalities");
    }

    public Municipality create(Map<String, Object> objectData){
        //todo remove
        System.out.println("service raggiunto");
        Map<String, Object> geoLoc = (Map<String, Object>) objectData.get("geoLocation");
        GeoLocationService service = new GeoLocationService();
        GeoLocation geoLocation = service.create(geoLoc);
        Municipality municipality = new Municipality(
                (String)  objectData.get("name"), geoLocation);
        try {
            this.repository.create(municipality);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println(objectData);
        return municipality;
    }
//    /**
//     * Create a new Municipality via Builder
//     * @return Municipality
//     */
//    public Municipality create(Map<String, Object> data) {
//        String name = (String) data.get("name");
//
//        GeoLocationService geoLocationService = new GeoLocationService();
//        GeoLocation dbGeoLocation = geoLocationService.create((Map<String, Object>) data.get("geoLocation"));
//
//        Municipality municipality = new Municipality(name, dbGeoLocation);
//
//        try {
//            repository.create(municipality);
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        return municipality;
//    }
//
//    public ResultSet getById(Integer id)throws SQLException {
//         return this.repository.getById(id);
//    }

}
