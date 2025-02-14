package org.services;

import org.dataMappers.PoiMapper;
import org.models.municipalities.Municipality;
import org.models.GeoLocation;
import org.models.poi.Poi;
import org.models.users.RegisteredUser;
import org.repositories.PoiRepository;

import java.util.List;
import java.util.Map;

/**
 * Ha la responsabilità di gestire la logica di business connessa alla
 * manipolazione ed all'interazione con gli oggetti di tipo POI.
 */

public class PoiService extends Service<Poi> {

    private GeoLocationService geoLocationService;

    private MunicipalityService municipalityService;

    public PoiService() {
        super(new PoiRepository(), new PoiMapper());
        this.geoLocationService = new GeoLocationService();
        this.municipalityService = new MunicipalityService();
    }

    @Override
    public Poi getObjectById(long id) throws Exception {
        Map<String, Object> entityData =  this.repository.getById(id, null);
        if(entityData == null)
            return null;
        Poi poi = (Poi) this.mapper.mapDataToObject(entityData);
        GeoLocation geoLocation = geoLocationService.get(entityData);
        Municipality municipality = municipalityService.get(entityData);
        poi.setGeoLocation(geoLocation);
        poi.setMunicipality(municipality);
        this.eventManager.notify("POI get by id");
        return poi;
    }

    /**
     * Gestisce il servizio di creazione di una nuova entità a database
     * @param objectData struttura dati con informazioni dell'oggetto da creare
     * @return object Municipality l'oggetto creato e salvato nello strato di persistenza.
     * @throws Exception
     */
    @Override
    public Poi create(Map<String, Object> objectData) throws Exception{
        Poi poi = (Poi) this.mapper.mapDataToObject(objectData);

        if(objectData.get("municipality_id") != null){

            int municipality_id = (int) objectData.get("municipality_id");
            Municipality municipality = municipalityService.getObjectById(municipality_id);
            poi.setMunicipality(municipality);

            if(poi.isLogical()){
                poi.setGeoLocation(municipality.getGeoLocation());
            }else if(objectData.get("geoLocation") != null){
                GeoLocation geolocation = geoLocationService.create ((Map<String, Object>) objectData.get("geoLocation"));
                poi.setGeoLocation(geolocation);
            }else
                return null;
        }else
            return null;

        eventManager.notify("Nuovo Punto di interesse in attesa di validazione");
        poi =  (Poi) repository.create(poi, null);
        if(objectData.containsKey("status")){
            objectData.put("id", poi.getId());
            poi = this.setStatus(objectData);
            eventManager.notify("Nuovo Punto di interesse auto-validato");
        }
        return poi;
    }

    public List<Poi> getByMunicipalityId(long id) throws Exception {
        List<Map<String, Object>> results =  ((PoiRepository)this.repository).getByMunicipalityId(id);
        return this.mapper.mapDbDataToObjects(results);
    }
}