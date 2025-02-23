package org.poifinder.services;

import jakarta.transaction.Transactional;
import org.poifinder.dataMappers.DataMapper;
import org.poifinder.dataMappers.municipalities.MunicipalityListMapper;
import org.poifinder.httpServer.auth.UserContext;
import org.poifinder.models.municipalities.Municipality;
import org.poifinder.models.poi.Poi;
import org.poifinder.models.users.RegisteredUser;
import org.poifinder.repositories.MunicipalityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class MunicipalityService extends BaseService<Municipality> {

    @Autowired
    private MunicipalityRepository municipalityRepository;

    private GeoLocationService geoLocationService;

    private PoiService poiService;

    @Autowired
    public MunicipalityService(MunicipalityRepository repository,
                               MunicipalityListMapper municipalityListMapper,
                               GeoLocationService geoLocationService,
//                               PoiService poiService
                               MunicipalityRepository municipalityRepository) {
        super(repository, municipalityListMapper);
        this.geoLocationService = geoLocationService;
//        this.poiService = poiService;
        this.municipalityRepository = municipalityRepository;
    }


    public Municipality get(Map<String, Object> result){
//        return (Municipality) this.mapper.mapDataToObject(result);
        return null;
    }


    /**
     * Servizio di ricerca di un Comune in base al nome e/o
     * ad un termine di ricerca
     * @param municipality
     * @param search
     * @return
     */
    public List<Municipality> search(String municipality, String search) {
        if (municipality != null && search != null) {
            return municipalityRepository.searchByMunicipalityAndSearch(municipality, search);
        } else if (municipality != null) {
            return municipalityRepository.searchByMunicipality(municipality);
        } else if (search != null) {
            return municipalityRepository.searchBySearch(search);
        } else {
            return repository.findAll();
        }
    }

    @Override
    public List<Municipality> filter(Map<String, String> queryParams) throws Exception {
        return List.of();
    }

//    @Override
//    public Municipality setStatus(Map<String, Object> data) throws Exception {
//        return null;
//    }

    /**
     * Gestisce il servizio di creazione di una nuova entità a database
     * @param municipality l'oggetto con le informazioni da creare
     * @return object Municipality l'oggetto creato e salvato nello strato di persistenza.
     * @throws Exception
     */
//    @Override
    public Municipality create(Municipality municipality) throws Exception{
        if(municipalityRepository.existsByName(municipality.getName()))
            return null;
        municipality.setAuthor(UserContext.getCurrentUser());
        municipality.setApprover(UserContext.getCurrentUser());
        return municipalityRepository.save(municipality);
    }


    public Municipality getObjectById(Long id){
        return municipalityRepository.getById(id);
    }

//    @Override
    public Municipality update(long id, Municipality municipality) throws Exception {//        Municipality municipality = this.getObjectById(id);
//        Municipality modifiedMunicipality = (Municipality) this.mapper.mapDataToObject(objectData);
//        GeoLocation modifiedGeolocation = geoLocationService.update(municipality.getGeoLocation().getId(), objectData);
//        modifiedMunicipality.setGeoLocation(modifiedGeolocation);
//        modifiedMunicipality.setAuthor(UserContext.getCurrentUser());
//        modifiedMunicipality.setId(id);
//        modifiedMunicipality = (Municipality) this.repository.update(modifiedMunicipality, null);
//        //todo implements notification and autovalidation
//        eventManager.notify("Nuovo Punto di interesse auto-validato", null);
//        return modifiedMunicipality;
        return null;
    }


    /**
     * Rimuove un municipio e tutti i poi e gli utenti ad esso assegnato.
     * @param id dell'utente da rimuovere
     * @return l'utente di default riassegnatario.
     * @throws Exception
     */
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

    /**
     * Ritorna un Comune correadato della sua lista di Punti di interesse
     * @param id l'id del comune di interesse
     * @return Municipality municipality il comune con i suoi punti di interesse.
     * @throws Exception
     */
//    public Municipality getWithPois(Long id) throws IOException,Exception {
//        Municipality municipality = super.getObjectById(id);
//        PoiService poiService = new PoiService();
//        ArrayList pois = (ArrayList) poiService.getByMunicipalityId(id);
//        municipality.setPois(pois);
//        return municipality;
//        return null;
//    }
}


