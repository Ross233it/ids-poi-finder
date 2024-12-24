package org.services;

import org.models.users.RegisteredUser;
import org.models.users.User;
import org.repositories.RegisteredUserRepository;

import java.util.Map;

/**
 * Ha la responsabilità di gestire la logica di business connessa alla
 * manipolazione ed all'interazione con gli oggetti di tipo POI.
 */

public class UserService implements IService<User> {
//    @Override
    RegisteredUserRepository repository;

    public UserService() {
        this.repository = new RegisteredUserRepository("users");
    }


    @Override
    public String index() {
        return "";
    }

    /**
     * Crea un nuovo poi partendo da una serie di dati già validati
     * @param objectData
     * @return
     */
    public  User create(Map<String, Object> objectData){

        System.out.println("service service data");
        System.out.println(objectData);
        //todo encrypt password
        User user = new RegisteredUser(
                (String)  objectData.get("username"),
                (String)  objectData.get("email"),
                (String)  objectData.get("password"),
                (String)  objectData.get("role")
                );
        try {
            this.repository.create(user);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println(objectData);
        return user;
    }

    @Override
    public String getById(String id) {
        return "";
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
