package org.poifinder.services.JavaFirstVersion;

import org.poifinder.dataMappers.GeoLocationMapper;
import org.poifinder.models.GeoLocation;
import org.poifinder.repositories.GeoLocationRepository;

import java.util.Map;

//@Service
public class GeoLocationService extends BaseService<GeoLocation> {

//    @Autowired
    public GeoLocationService(GeoLocationRepository repository, GeoLocationMapper mapper) {
        super(repository,  mapper);
    }

    public GeoLocationService() {
        super(new GeoLocationRepository(),  new GeoLocationMapper());
    }

    public GeoLocation get(Map<String, Object> result){
        return (GeoLocation) this.mapper.mapDataToObject(result);
    }
}
