package org.poifinder.services;

import org.poifinder.dataMappers.GeoLocationMapper;
import org.poifinder.models.GeoLocation;
import org.poifinder.repositories.GeoLocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Map;

@Service
public class GeoLocationService extends BaseService<GeoLocation> {

    @Autowired
    public GeoLocationService(GeoLocationRepository repository, GeoLocationMapper mapper) {
        super(repository,  mapper);    }


    public GeoLocation get(Map<String, Object> result){
        return (GeoLocation) this.mapper.mapDataToObject(result);
    }

    @Override
    public List<GeoLocation> filter(Map<String, String> queryParams) throws Exception {
        return List.of();
    }

    @Override
    public GeoLocation setStatus(Map<String, Object> data) throws Exception {
        return null;
    }

    @Override
    public GeoLocation delete(long id) throws Exception {
        return null;
    }
}
