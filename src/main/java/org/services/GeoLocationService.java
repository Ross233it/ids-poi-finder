package org.services;

import org.dataMappers.GeoLocationMapper;
import org.models.GeoLocation;
import org.repositories.GeoLocationRepository;


import java.util.Map;

public class GeoLocationService  extends Service<GeoLocation> {

    public GeoLocationService() {
        super(new GeoLocationRepository(),  new GeoLocationMapper());
    }

    public GeoLocation get(Map<String, Object> result){
        return (GeoLocation) this.mapper.mapDataToObject(result);
    }

    /**
     * Recupera o crea un oggetto geolocation
     * @param geoLocData
     * @return l'oggetto geolocation se esistente | null altrimenti
     * @throws Exception
     */
    public GeoLocation getOrCreate(Map<String, Object> geoLocData) throws Exception {
        Map<String, Object> geoLoc = (Map<String, Object>) geoLocData.get("geoLocation");
        GeoLocation geoLocation = null;
        Long geoLocId = null;
             geoLocId = (Long) geoLocData.get("geoLocation_id");
        if(geoLocId == null)
            geoLocId = (Long) geoLocData.get("id");

        if(geoLoc != null)
            geoLocation = this.create(geoLoc);
        else if (geoLocId != null) {
            geoLocation = this.getObjectById(geoLocId);
        }
        return geoLocation;
    }
}
