package org.services;

import org.dataMappers.MunicipalityMapper;
import org.models.GeoLocation;
import org.models.municipalities.Municipality;
import org.repositories.MunicipalityRepository;


import java.io.IOException;
import java.util.Map;


public class MunicipalityService extends Service<Municipality> {

    private GeoLocationService geoLocationService;

    public MunicipalityService() {
        super(new MunicipalityRepository(), new MunicipalityMapper());
        this.geoLocationService = new GeoLocationService();
    }

    public Municipality get(Map<String, Object> result){
        return (Municipality) this.mapper.mapDataToObject(result);
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
        return (Municipality) repository.create(municipality, null);
    }


    /**
     * Ritorna un Comune correadato della sua lista di Punti di interesse
     * @param id l'id del comune di interesse
     * @return Municipality municipality il comune con i suoi punti di interesse.
     * @throws Exception
     */
    public Municipality getWithPois(Long id) throws IOException,Exception {
//        Municipality municipality = super.getObjectById(id);
//        ArrayList<Poi> pois = new ArrayList<>();
//        municipality.setPois(new PoiService(new PoiRepository()).getByMunicipalityId(id));
//        return municipality;
        return null;
    }
}


