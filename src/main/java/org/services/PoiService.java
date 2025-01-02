package org.services;
import org.models.Municipality;
import org.models.informations.GeoLocation;
import org.models.poi.Poi;
import org.models.poi.IPoi;
import org.models.poi.PoiBuilder;
import org.repositories.PoiRepository;

import java.util.Map;

/**
 * Ha la responsabilità di gestire la logica di business connessa alla
 * manipolazione ed all'interazione con gli oggetti di tipo POI.
 */

public class PoiService extends Service<IPoi> {

    public PoiService(PoiRepository repository) {
        super(repository);
    }

    public String index() {
        try {
            return this.repository.index();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public IPoi getObjectById(int id) throws Exception {
        try {
            Map<String, Object> poiData = ((PoiRepository) this.repository).getById(id, "");
            if (poiData == null) {
                return null;
            }else{
                GeoLocationService geoLocationService   = new GeoLocationService();
                MunicipalityService municipalityService = new MunicipalityService();
                GeoLocation geoLocation   = geoLocationService.getObjectById((Integer) poiData.get("geolocation_id"));
                Municipality municipality = municipalityService.getObjectById((Integer) poiData.get("municipality_id"));

                return new PoiBuilder(
                        (String) poiData.get("name"),
                        (String) poiData.get("description"),
                        (Boolean) poiData.get("is_logical")).
                        geoLocation(geoLocation).municipality(municipality).build();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Crea un nuovo poi partendo da una serie di dati già validati
     * @param objectData
     * @return
     */
    @Override
    public IPoi create(Map<String, Object> objectData){
        Map<String, Object> geoLoc = (Map<String, Object>) objectData.get("geoLocation");
        GeoLocationService service = new GeoLocationService();
        GeoLocation geoLocation = service.create(geoLoc);
        Poi poi = new PoiBuilder(
                (String)  objectData.get("name"),
                (String)  objectData.get("description"),
                (Boolean) objectData.get("isLogical")).
                geoLocation(geoLocation).build();
        try {
            this.repository.create(poi);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println(objectData);
        return poi;
    }
}
