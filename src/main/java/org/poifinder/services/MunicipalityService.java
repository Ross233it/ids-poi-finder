package org.poifinder.services;

import org.poifinder.dataMappers.DataMapper;
import org.poifinder.dataMappers.municipalities.MunicipalityListMapper;
import org.poifinder.models.municipalities.Municipality;
import org.poifinder.models.poi.Poi;
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

    @Override
    public Municipality setStatus(Map<String, Object> data) throws Exception {
        return null;
    }

    /**
     * Gestisce il servizio di creazione di una nuova entità a database
     *
     * @param objectData struttura dati con informazioni dell'oggetto da creare
     * @return object Municipality l'oggetto creato e salvato nello strato di persistenza.
     * @throws Exception
     */
    @Override
    public DataMapper<Municipality> create(DataMapper<Municipality> objectData) throws Exception{
//        GeoLocation geoLocation = geoLocationService.create(objectData);
//        Municipality municipality = (Municipality) this.mapper.mapDataToObject(objectData);
//        municipality.setGeoLocation(geoLocation);
//        municipality.setAuthor((RegisteredUser) objectData.get("author"));
//        return (Municipality) repository.create(municipality, null);
        return null;
    }

//    @Override
    public Municipality update(long id, Municipality municipality) throws Exception {
//        Municipality municipality = this.getObjectById(id);
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

    @Override
    public Municipality delete(long id) throws Exception {
        return null;
    }

    /**
     * Ritorna un Comune correadato della sua lista di Punti di interesse
     * @param id l'id del comune di interesse
     * @return Municipality municipality il comune con i suoi punti di interesse.
     * @throws Exception
     */
    public Municipality getWithPois(Long id) throws IOException,Exception {
//        Municipality municipality = super.getObjectById(id);
//        PoiService poiService = new PoiService();
//        ArrayList pois = (ArrayList) poiService.getByMunicipalityId(id);
//        municipality.setPois(pois);
//        return municipality;
        return null;
    }
}


