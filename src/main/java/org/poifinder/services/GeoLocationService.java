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


    /**
     * Servizio di creazione di una posizione geolocalizzata
     * @param geolocation la geolocalizzazione da memorizzare
     * @return la geolocalizzazione memorizzata
     * @throws Exception
     */
    public GeoLocation create(GeoLocation geolocation) throws Exception {
        return this.repository.save(geolocation);
    }


    public GeoLocation get(Map<String, Object> result){
//        return (GeoLocation) this.mapper.mapDataToObject(result);
        return null;
    }

    @Override
    public List<GeoLocation> filter(Map<String, String> queryParams) throws Exception {
        return List.of();
    }

//    @Override
//    public GeoLocation setStatus(Long id, String status) throws Exception {
//        return super(id, status);
//    }

    @Override
    public GeoLocation delete(Long id) throws Exception {
        return null;
    }
}
