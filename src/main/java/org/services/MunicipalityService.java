package org.services;


import org.models.Municipality;
import org.models.informations.GeoLocation;
import org.repositories.BaseRepository;
import org.repositories.GeoLocationRepository;

import java.util.Map;

public class MunicipalityService implements Service<Municipality>{

    /**
     * Create a new Municipality via Builder
     * @return Municipality
     */
    public Municipality create(Map<String, Object> data) {
        Map<String, Object> geoLocationData = (Map<String, Object>) data.get("geoLocation");
        String name = (String) geoLocationData.get("name");
        GeoLocation geoLocation  = new GeoLocation(
               1,
               (String)geoLocationData.get("address"),
               (String)geoLocationData.get("number"),
               (String)geoLocationData.get("cap"));
        BaseRepository connector = new GeoLocationRepository();
        try{
            connector.openConnection();

        }
        catch(Exception e){
            e.printStackTrace();
        }
        connector.closeConnection();
       return new Municipality(name, geoLocation);
    }

}
