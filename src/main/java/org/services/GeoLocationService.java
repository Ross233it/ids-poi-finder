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

}
