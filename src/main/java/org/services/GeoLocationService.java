package org.services;

import org.dataMappers.GeoLocationMapper;
import org.models.GeoLocation;
import org.repositories.GeoLocationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Map;

@Service
public class GeoLocationService extends BaseService<GeoLocation> {

    @Autowired
    public GeoLocationService(GeoLocationRepository repository,  GeoLocationMapper mapper) {
        super(repository,  mapper);
    }

    public GeoLocationService() {
        super(new GeoLocationRepository(),  new GeoLocationMapper());
    }

    public GeoLocation get(Map<String, Object> result){
        return (GeoLocation) this.mapper.mapDataToObject(result);
    }
}
