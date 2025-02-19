package org.services;

import org.dataMappers.MunicipalityMapper;
import org.httpServer.auth.UserContext;
import org.models.GeoLocation;
import org.models.municipalities.Municipality;
import org.models.users.RegisteredUser;
import org.repositories.MunicipalityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

@Service
public class MunicipalityService extends BaseService<Municipality> {

    private GeoLocationService geoLocationService;

    private PoiService poiService;

    @Autowired
    public MunicipalityService(MunicipalityRepository municipalityRepository,
                               MunicipalityMapper municipalityMapper,
                               GeoLocationService geoLocationService,
                               PoiService poiService) {
        super(municipalityRepository, municipalityMapper);
        this.geoLocationService = geoLocationService;
        this.poiService = poiService;
    }

    public MunicipalityService() {
        super(new MunicipalityRepository(), new MunicipalityMapper());
        this.geoLocationService = new GeoLocationService();
    }


    public Municipality get(Map<String, Object> result){
        return (Municipality) this.mapper.mapDataToObject(result);
    }


    @Override
    public Municipality getObjectById(long id) throws Exception {
        Map<String, Object> entityData =  this.repository.getById(id, null);
        if(entityData == null)
            return null;
        Municipality municipality= (Municipality) this.mapper.mapDataToObject(entityData);
        GeoLocation geoLocation = geoLocationService.get(entityData);
        municipality.setGeoLocation(geoLocation);
        return municipality;
    }

    /**
     * Gestisce il servizio di creazione di una nuova entità a database
     * @param objectData struttura dati con informazioni dell'oggetto da creare
     * @return object Municipality l'oggetto creato e salvato nello strato di persistenza.
     * @throws Exception
     */
    @Override
    public Municipality create(Map<String, Object> objectData) throws Exception{
        GeoLocation geoLocation = geoLocationService.create(objectData);
        Municipality municipality = (Municipality) this.mapper.mapDataToObject(objectData);
        municipality.setGeoLocation(geoLocation);
        municipality.setAuthor((RegisteredUser) objectData.get("author"));
        return (Municipality) repository.create(municipality, null);
    }

    @Override
    public Municipality update(long id, Map<String, Object> objectData) throws Exception {
        Municipality municipality = this.getObjectById(id);
        Municipality modifiedMunicipality = (Municipality) this.mapper.mapDataToObject(objectData);
        GeoLocation modifiedGeolocation = geoLocationService.update(municipality.getGeoLocation().getId(), objectData);
        modifiedMunicipality.setGeoLocation(modifiedGeolocation);
        modifiedMunicipality.setAuthor(UserContext.getCurrentUser());
        modifiedMunicipality.setId(id);
        modifiedMunicipality = (Municipality) this.repository.update(modifiedMunicipality, null);
        //todo implements notification and autovalidation
        eventManager.notify("Nuovo Punto di interesse auto-validato", null);
        return modifiedMunicipality;
    }

    /**
     * Ritorna un Comune correadato della sua lista di Punti di interesse
     * @param id l'id del comune di interesse
     * @return Municipality municipality il comune con i suoi punti di interesse.
     * @throws Exception
     */
    public Municipality getWithPois(Long id) throws IOException,Exception {
        Municipality municipality = super.getObjectById(id);
        PoiService poiService = new PoiService();
        ArrayList pois = (ArrayList) poiService.getByMunicipalityId(id);
        municipality.setPois(pois);
        return municipality;
    }
}


