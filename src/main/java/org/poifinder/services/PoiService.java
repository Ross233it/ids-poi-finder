package org.poifinder.services;

import org.poifinder.dataMappers.poi.PoiCreateMapper;
import org.poifinder.dataMappers.poi.PoiListMapper;
import org.poifinder.dataMappers.poi.PoiMapper;

import org.poifinder.models.municipalities.Municipality;
import org.poifinder.models.GeoLocation;
import org.poifinder.models.poi.Poi;
import org.poifinder.models.poi.PoiBuilder;
import org.poifinder.repositories.PoiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Ha la responsabilità di gestire la logica di business connessa alla
 * manipolazione ed all'interazione con gli oggetti di tipo POI.
 */
@Service
@Primary
public class PoiService extends BaseService<Poi> {

    @Autowired
    private GeoLocationService geoLocationService;

    @Autowired
    private MunicipalityService municipalityService;

    @Autowired
    private PoiRepository poiRepository;

    @Autowired
    public PoiService(PoiRepository repository,
                      PoiMapper mapper,
                      MunicipalityService municipalityService,
                      GeoLocationService geoLocationService) {
        super(repository, mapper);
        this.geoLocationService = geoLocationService;
        this.municipalityService = municipalityService;
    }

    /**
     * Servizio di ricerca di poi in base al comune e/o ad un termine di ricerca
     * @param municipality
     * @param search
     * @return
     */
    public List<Poi> search(String municipality, String search) {
        if (municipality != null && search != null) {
            return poiRepository.searchByMunicipalityAndSearch(municipality, search);
        } else if (municipality != null) {
            return poiRepository.searchByMunicipality(municipality);
        } else if (search != null) {
            return poiRepository.searchBySearch(search);
        } else {
            return repository.findAll();
        }
    }



    @Override
    public List<Poi> filter(Map<String, String> queryParams) throws Exception {
        return List.of();
    }

    @Override
    public Poi setStatus(Map<String, Object> data) throws Exception {
        return null;
    }


    /**
     * Gestisce il servizio di creazione di una nuova entità a database
     * @param poiCreateMapper struttura dati con informazioni dell'oggetto da creare
     * @return object Municipality l'oggetto creato e salvato nello strato di persistenza.
     * @throws Exception
     */


    public PoiListMapper create(PoiCreateMapper poiCreateMapper) throws Exception {
//        if (poiRepository.existsByName(poiCreateMapper.getName())) {
//            throw new RuntimeException("Poi con questo nome già esistente");
//        }

        Municipality municipality = municipalityService.getObjectById(poiCreateMapper.getMunicipality_id());

        System.out.println("IO SONO il municipio");
        System.out.println(municipality);

        if(municipality == null){
            throw new RuntimeException("Comune non trovato");
        }

        System.out.println("IO SONO il municipio");
        System.out.println(municipality);


        GeoLocation geolocation = new GeoLocation(
                poiCreateMapper.getAddress(),
                poiCreateMapper.getNumber(),
                poiCreateMapper.getCap());
        if(geolocation != null){
                geolocation.setLatitude(poiCreateMapper.getLatitude());
                geolocation.setLongitude(poiCreateMapper.getLongitude());
        }

        System.out.println("IO SONO GEOLOCATION");
        System.out.println(geolocation);

        geoLocationService.create(geolocation);

        PoiBuilder poiBuilder = new PoiBuilder(
                poiCreateMapper.getName(),
                poiCreateMapper.getDescription(),
                poiCreateMapper.isLogical());


        Poi newPoi = poiBuilder
                .type(poiCreateMapper.getType())
                .municipality(municipality)
                .geoLocation(geolocation)
                .build();

        System.out.println("IO SONO NEW POI");
        System.out.println(newPoi);

        poiRepository.save(newPoi);

        eventManager.notify("Nuovo Punto di interesse in attesa di validazione", null);

        return new PoiListMapper(newPoi.getId(), newPoi.getName(), newPoi.getDescription(),
                newPoi.getType(), newPoi.isLogical(), newPoi.getGeoLocation());
    }



//    @Override
//    public Poi create(Poi objectData) throws Exception{
//        Poi poi = (Poi) this.mapper.mapDataToObject(objectData);
//
//        if(objectData.get("municipality_id") != null){
//            int municipalityId = (int) objectData.get("municipality_id");
//            Municipality municipality = municipalityService.getObjectById(municipalityId);
//            poi.setMunicipality(municipality);
//
//            if(poi.isLogical()){
//                poi.setGeoLocation(municipality.getGeoLocation());
//            }else if(objectData.get("geoLocation") != null){
//                GeoLocation geolocation = geoLocationService.create ((Map<String, Object>) objectData.get("geoLocation"));
//                poi.setGeoLocation(geolocation);
//            }else
//                return null;
//        }else
//            return null;

//        eventManager.notify("Nuovo Punto di interesse in attesa di validazione", null);
//        poi =  (Poi) repository.create(poi, null);
//        if(objectData.containsKey("status")){
//            objectData.put("id", poi.getId());
//            poi = this.setStatus(objectData);
//            eventManager.notify("Nuovo Punto di interesse auto-validato", null);
//        }
//        return poi;
//        return null;
//    }

//    @Override
    public Poi update(long id, Poi poi) throws Exception {
//        Poi poi = this.getObjectById(id);
//        if(poi != null){
//            poi.setAuthor(UserContext.getCurrentUser());
//
////            if(authorId != UserContext.getCurrentUser().getId())
////                return null;
//        }
//        Poi modifiedPoi = (Poi) this.mapper.mapDataToObject(objectData);
//        poi.setStatus("pending");
//        GeoLocation modifiedGeolocation = geoLocationService.update(poi.getGeoLocation().getId(), objectData);
//        modifiedPoi.setGeoLocation(modifiedGeolocation);
//        modifiedPoi = (Poi) this.repository.update(modifiedPoi , null);
//
//        //todo implements notification and autovalidation
//        eventManager.notify("Nuovo Punto di interesse auto-validato", null);
//        return modifiedPoi;
        return null;
    }

    @Override
    public Poi delete(long id) throws Exception {
        return null;
    }

    public List<Poi> getByMunicipalityId(long id) throws Exception {
//        List<Map<String, Object>> results =  ((PoiRepository)this.repository).getByMunicipalityId(id);
//        return this.mapper.mapDbDataToObjects(results);

        return poiRepository.getByMunicipalityId(id);

    }

//    /**
//     * Servizio di segnalazione di un poi ad un utente
//     * responsabile.
//     * @param request
//     */
//    public void reportContent(HttpRequest request) throws Exception{
//        long id = request.getRequestId();
//        Map<String, Object> reportData = request.getBodyStreamData();
//        Map<String, Object> entityData =  this.baseRepository.getById(id, null);
//        Poi poi = (Poi) this.mapper.mapDataToObject(entityData);
//        reportData.put("author_email", poi.getAuthor().getEmail());
//        reportData.put("approver_email", poi.getApprover().getEmail());
//        reportData.put("poi_content", poi.getData());
//        this.eventManager.notify("content report", entityData);
//    }
}