package org.services;
import org.models.informations.GeoLocation;
import org.models.poi.Poi;
import org.models.poi.IPoi;
import org.models.poi.PoiBuilder;
import org.repositories.IRepository;import org.repositories.PoiRepository;


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
    public String getById(String id) {
        try {
            return this.repository.getById(Integer.parseInt(id), null);
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
