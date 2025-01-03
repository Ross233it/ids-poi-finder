package org.services;

import org.models.Municipality;
import org.models.GeoLocation;
import org.repositories.GeoLocationRepository;
import org.repositories.MunicipalityRepository;
import java.util.Map;

public class MunicipalityService extends Service<Municipality> {


    public MunicipalityService(MunicipalityRepository repository) {
        super(repository);
    }

    @Override
    public String index() {
        return "";
    }

    public Municipality create(Map<String, Object> objectData)throws Exception{
        Map<String, Object> geoLoc = (Map<String, Object>) objectData.get("geoLocation");
        GeoLocationService service = new GeoLocationService(new GeoLocationRepository("geolocations"));
        GeoLocation geoLocation = service.create(geoLoc);
        Municipality municipality = new Municipality(
                (String)  objectData.get("name"),
                (String)  objectData.get("province"),
                (String)  objectData.get("region")
                );
        municipality.setGeoLocation(geoLocation);
        try {
            this.repository.create(municipality);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println(objectData);
        return municipality;
    }


    /**
     * Ritorna un oggetto Comune in base all'id e ai dati recuperati dallo strato di persistenza.
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public Municipality getObjectById(long id) throws Exception {
        Map<String, Object> municipalityData = (this.repository).getById(id, "");
        if(municipalityData == null)
            return null;
        GeoLocationService geoLocationService = new GeoLocationService(new GeoLocationRepository("geolocations"));
        Integer geoLocationId = ((Long) municipalityData.get("geolocation_id")).intValue();
        GeoLocation geoLocation = geoLocationService.getObjectById(geoLocationId);
        Municipality municipality = new Municipality(
                (String) municipalityData.get("name"),
                geoLocation);
        municipality.setId(id);
        return municipality;
    }
}
