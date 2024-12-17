package org.services;
import org.models.informations.GeoLocation;
import org.models.poi.BasePoi;
import org.models.poi.Poi;
import org.models.poi.PoiBuilder;
import org.repositories.PoiRepository;

import java.util.Map;

/**
 * Ha la responsabilità di gestire la logica di business connessa alla
 * manipolazione ed all'interazione con gli oggetti di tipo POI.
 */

public class PoiService implements Service<Poi> {
//    @Override
    PoiRepository repository;

    public PoiService() {
        this.repository = new PoiRepository("pois");
    }


    /**
     * Crea un nuovo poi partendo da una serie di dati già validati
     * @param objectData
     * @return
     */
    public  Poi create(Map<String, Object> objectData){
        Map<String, Object> geoLoc = (Map<String, Object>) objectData.get("geoLocation");
        GeoLocationService service = new GeoLocationService();
        GeoLocation geoLocation = service.create(geoLoc);
        BasePoi poi = new PoiBuilder(
                (String)  objectData.get("name"),
                (String)  objectData.get("description"),
                (Boolean) objectData.get("isLogical")).
                geoLocation(geoLocation).build();
        try {
            this.repository.create(poi);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println(objectData);
        return poi;
    }

//    public Poi create(Map<String, Object> data) throws SQLException {
//        String name = (String) data.get("name");
//        String description = (String) data.get("description");
//        Boolean isLogical  = (Boolean) data.get("isLogical");
//        Integer municipalityId = (Integer) data.get("municipalityId");
//
//
//        MunicipalityService municipalityService = new MunicipalityService();
//        ResultSet dbMunicipality = (ResultSet) municipalityService.getById(municipalityId);
//        //todo
//        while (dbMunicipality.next()) {
//            int id = dbMunicipality.getInt("id");
//            String myname = dbMunicipality.getString("name");
//            int geolocationId = dbMunicipality.getInt("geolocation_id");
//
//            System.out.println(id + " | " + myname + " | " + geolocationId);
//        }
//
//
//        Municipality municipality = new Municipality();
//
//        GeoLocationService geoLocationService = new GeoLocationService();
//        GeoLocation dbGeoLocation = geoLocationService.create((Map<String, Object>) data.get("geoLocation"));
//
////        Poi poi = new BasePoi(name, description, isLogical, municipality,  dbGeoLocation);
//
//        GeoLocationRepository geoLocationRepository = new GeoLocationRepository();
////        try {
////            geoLocationRepository.create(municipality);
////        } catch (SQLException e) {
////            throw new RuntimeException(e);
////        }
////        return poi;
//    };

}
