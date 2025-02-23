package org.poifinder.services;

import org.poifinder.dataMappers.poi.PoiMapper;

import org.poifinder.httpServer.auth.UserContext;
import org.poifinder.models.municipalities.Municipality;
import org.poifinder.models.GeoLocation;
import org.poifinder.models.poi.Poi;
import org.poifinder.models.users.RegisteredUser;
import org.poifinder.repositories.MunicipalityRepository;
import org.poifinder.repositories.PoiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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
    private MunicipalityRepository municipalityRepository;

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

    /**
     * Recupera un oggetto Poi in base all'id e ne memorizza lo stato
     * @param id identificativo dell'entità
     * @param status lo stato da memorizzare per l'entità
     * @return poi l'oggetto con lo stato settato.
     * @throws Exception
     */
    @Override
    public Poi setStatus(Long id, String status) throws Exception {
        Poi poi = getObjectById(id);
        if(poi != null && poi.getStatus().equals("pending")){
            RegisteredUser approver = UserContext.getCurrentUser();
            //approvazione solo dei comuni di propria competenza
            if(approver.getMunicipality().getId() == poi.getMunicipality().getId()){
                if(status.equals("rejected"))
                    this.delete(id);
                poi.getGeoLocation().setStatus(status);
                poi.setApprover(approver);
                poi.setStatus(status);
                return poiRepository.save(poi);
            }
        }
        return null;
    }

    /**
     * Gestisce il servizio di creazione di una nuova entità a database
     * @param poi informazioni dell'oggetto da creare
     * @return object Municipality l'oggetto creato e salvato nello strato di persistenza.
     * @throws Exception
     */
    @Override
    public Poi create(Poi poi) throws Exception {
        RegisteredUser author = UserContext.getCurrentUser();
        Municipality municipality = municipalityService.getObjectById(poi.getMunicipality_id());

        if (author == null || municipality == null) {
            throw new RuntimeException();
        }

        if (poi.getIsLogical()) {
            GeoLocation geolocation = municipality.getGeoLocation();
            poi.setGeoLocation(geolocation);
        }

        poi.setMunicipality(municipality);
        poi.setAuthor(author);
        poi = (Poi) publishOrPending(poi);

        if (poi.getMunicipality().getId() == poi.getAuthor().getMunicipality().getId()) {
            Poi newPoi = poiRepository.save(poi);
            this.notify(newPoi);
            return newPoi;
        }
        return null;
    }

    /**
     * Riassegna tutti i poi che un utente ha pubblicato o approvato ad un altro
     * autore - responsabile
     * @param currentUser l'attuale autore - responsabile del poi
     * @param newUser  l'utente a cui il poi viene riassegnato
     */
    public void setAuthorAndApproverMassively(RegisteredUser currentUser, RegisteredUser newUser){
        List<Poi> poisAsAuthor = poiRepository.findByAuthor(currentUser);
        for (Poi poi : poisAsAuthor) {
            poi.setAuthor(newUser);
        }
        List<Poi> poisAsApprover = poiRepository.findByApprover(currentUser);
                for (Poi poi : poisAsApprover) {
            poi.setApprover(newUser);
        }
        poiRepository.saveAll(poisAsAuthor);
        poiRepository.saveAll(poisAsApprover);
    }
//
//    /**
//     * Verifica se l'autore di un poi dispone dei privilegi necessari
//     * per l'autopubblicazione di un poi.
//     * @param poi da valutare
//     * @return il poi autopubblicato se l'utente dispone dei privilegi.
//     */
//    private Poi publishOrPending(Poi poi){
//        List<String>roles = List.of("authContributor","curator", "animator", "platformAdmin");
//        String eventType =  "new-pending-poi";
//        if(poi.getAuthor().hasOneRole(roles)
//           && poi.getMunicipality().getId() == poi.getAuthor().getMunicipality().getId()) {
//            poi.setStatus("published");
//            eventType = "new-published-poi";
//        }
//        Map<String, Object>eventData = Map.of("NuovoPoi", poi);
//        eventManager.notify(eventType, eventData);
//        return poi;
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

    /**
     * Rimuove il poi senza interferire con i dati del comune
     * @param id l'identificativo del Poi da rimuovere
     * @return il poi eliminato
     * @throws Exception
     */
    @Override
    public Poi delete(Long id) throws Exception {
        Poi toDelete = poiRepository.getById(id);
        toDelete.setMunicipality(null);
        poiRepository.delete(toDelete);
        return toDelete;
    }

    //    @Override
//    @Transactional
//    public Municipality delete(long id) throws Exception {
//
//            Municipality userToDelete = municipalityRepository.findById(id)
//                    .orElseThrow(() -> new RuntimeException("Muunicipio non trovato"));
//
//
//        }
//
//    }

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