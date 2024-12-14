package org.services;
import org.models.Municipality;
import org.models.informations.GeoLocation;
import org.models.poi.PhisicalPoi;
import org.models.poi.Poi;
import org.repositories.GeoLocationRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

/**
 * Ha la responsabilità di gestire la logica di business connessa alla
 * manipolazione ed all'interazione con gli oggetti di tipo POI.
 */

public class PoiService implements Service<Poi> {

    public Poi create(Map<String, Object> data) throws SQLException {
        String name = (String) data.get("name");
        String description = (String) data.get("description");
        Boolean isLogical  = (Boolean) data.get("isLogical");
        Integer municipalityId = (Integer) data.get("municipalityId");


        MunicipalityService municipalityService = new MunicipalityService();
        ResultSet dbMunicipality = (ResultSet) municipalityService.getById(municipalityId);
        //todo
        while (dbMunicipality.next()) {
            int id = dbMunicipality.getInt("id");
            String myname = dbMunicipality.getString("name");
            int geolocationId = dbMunicipality.getInt("geolocation_id");

            System.out.println(id + " | " + myname + " | " + geolocationId);
        }


        Municipality municipality = new Municipality();

        GeoLocationService geoLocationService = new GeoLocationService();
        GeoLocation dbGeoLocation = geoLocationService.create((Map<String, Object>) data.get("geoLocation"));

        Poi poi = new PhisicalPoi(name, description, isLogical, municipality,  dbGeoLocation);

        GeoLocationRepository geoLocationRepository = new GeoLocationRepository();
//        try {
//            geoLocationRepository.create(municipality);
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
        return poi;
    };

}
