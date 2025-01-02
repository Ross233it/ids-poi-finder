package org.services;


import org.models.Municipality;
import org.models.informations.GeoLocation;
import org.repositories.MunicipalityRepository;

import java.io.ObjectStreamException;
import java.util.Map;

public class MunicipalityService implements IService<Municipality> {


    private final MunicipalityRepository repository;


    public MunicipalityService() {
        this.repository = new MunicipalityRepository("municipalities");
    }

    @Override
    public String index() {
        return "";
    }

    public Municipality create(Map<String, Object> objectData){

        Map<String, Object> geoLoc = (Map<String, Object>) objectData.get("geoLocation");
        GeoLocationService service = new GeoLocationService();
        GeoLocation geoLocation = service.create(geoLoc);
        Municipality municipality = new Municipality(
                (String)  objectData.get("name"), geoLocation);
        try {
            this.repository.create(municipality);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println(objectData);
        return municipality;
    }

    @Override
    public Municipality getObjectById(int id) throws Exception {
        return null;
    }

    @Override
    public Municipality delete(int id) throws Exception {
        return null;
    }
}
