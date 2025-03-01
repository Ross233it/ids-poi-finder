package org.poifinder.services;

import org.poifinder.models.GeoLocation;
import org.poifinder.repositories.GeoLocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Gesisce i servizi connessi alla classe GeoLocation
 */
@Service
public class GeoLocationService extends BaseService<GeoLocation> {

    @Autowired
    public GeoLocationService(GeoLocationRepository repository) {
        super(repository);
    }
}
