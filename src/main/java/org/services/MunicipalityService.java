package org.services;

import org.models.municipalities.Municipality;
import org.models.GeoLocation;
import org.models.poi.Poi;
import org.models.users.RegisteredUser;
import org.repositories.GeoLocationRepository;
import org.repositories.MunicipalityRepository;
import org.repositories.PoiRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class MunicipalityService extends Service<Municipality> {

    public MunicipalityService(MunicipalityRepository repository) {
        super(repository);
    }

    public Municipality create(Map<String, Object> objectData, RegisteredUser currentUser)throws Exception{
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
            this.repository.create(municipality, "");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println(objectData);
        return municipality;
    }


    /**
     * Ritorna un Comune correadato della sua lista di Punti di interesse
     * @param id l'id del comune di interesse
     * @return Municipality municipality il comune con i suoi punti di interesse.
     * @throws Exception
     */
    public Municipality getWithPois(Long id) throws IOException,Exception {
        Municipality municipality = super.getObjectById(id);
        ArrayList<Poi> pois = new ArrayList<>();
        municipality.setPois(new PoiService(new PoiRepository("pois")).getByMunicipalityId(id));
        return municipality;
    }


//todo refactor with builder
    @Override
    protected Municipality buildEntity(Map<String, Object> objectData)throws Exception{
        Municipality municipality = new Municipality(
                (String) objectData.get("name"),
                (String) objectData.get("region"),
                (String) objectData.get("province")
        );
        GeoLocationService geoLocationService = new GeoLocationService(new GeoLocationRepository("geolocations"));
        Long geoLocationId = ((Long) objectData.get("geolocation_id"));
        GeoLocation geoLocation = geoLocationService.getObjectById(geoLocationId);
        municipality.setGeoLocation(geoLocation);
        return municipality;
    }
}
