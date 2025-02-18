package org.services;

import org.dataMappers.MunicipalityMapper;
import org.dataMappers.PoiMapper;

import org.httpServer.auth.UserContext;
import org.models.municipalities.Municipality;
import org.models.GeoLocation;
import org.models.poi.Poi;
import org.repositories.MunicipalityRepository;
import org.repositories.PoiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Ha la responsabilità di gestire la logica di business connessa alla
 * manipolazione ed all'interazione con gli oggetti di tipo POI.
 */
@Service
public class PoiService extends BaseService<Poi> {

    private GeoLocationService geoLocationService;

    private MunicipalityService municipalityService;

    @Autowired
    public PoiService(PoiRepository repository,
                      PoiMapper mapper,
                      GeoLocationService geoLocationService,
                      PoiService poiService) {
        super(repository, mapper);
        this.geoLocationService = geoLocationService;
    }


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
            int municipalityId = (int) objectData.get("municipality_id");
            Municipality municipality = municipalityService.getObjectById(municipalityId);
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

        eventManager.notify("Nuovo Punto di interesse in attesa di validazione", null);
        poi =  (Poi) repository.create(poi, null);
        if(objectData.containsKey("status")){
            objectData.put("id", poi.getId());
            poi = this.setStatus(objectData);
            eventManager.notify("Nuovo Punto di interesse auto-validato", null);
        }
        return poi;
    }

    @Override
    public Poi update(long id, Map<String, Object> objectData) throws Exception {
        Poi poi = this.getObjectById(id);
        if(poi != null){
            long authorId = poi.getAuthor().getId();
            if(authorId != UserContext.getCurrentUser().getId())
                return null;
        }
        Poi modifiedPoi = (Poi) this.mapper.mapDataToObject(objectData);
        poi.setStatus("pending");
        GeoLocation modifiedGeolocation = geoLocationService.update(poi.getGeoLocation().getId(), objectData);
        modifiedPoi.setGeoLocation(modifiedGeolocation);
        modifiedPoi = (Poi) this.repository.update(modifiedPoi , null);

        //todo implements notification and autovalidation
        eventManager.notify("Nuovo Punto di interesse auto-validato", null);
        return modifiedPoi;
    }

    public List<Poi> getByMunicipalityId(long id) throws Exception {
        List<Map<String, Object>> results =  ((PoiRepository)this.repository).getByMunicipalityId(id);
        return this.mapper.mapDbDataToObjects(results);
    }

//    /**
//     * Servizio di segnalazione di un poi ad un utente
//     * responsabile.
//     * @param request
//     */
//    public void reportContent(HttpRequest request) throws Exception{
//        long id = request.getRequestId();
//        Map<String, Object> reportData = request.getBodyStreamData();
//        Map<String, Object> entityData =  this.repository.getById(id, null);
//        Poi poi = (Poi) this.mapper.mapDataToObject(entityData);
//        reportData.put("author_email", poi.getAuthor().getEmail());
//        reportData.put("approver_email", poi.getApprover().getEmail());
//        reportData.put("poi_content", poi.getData());
//        this.eventManager.notify("content report", entityData);
//    }
}