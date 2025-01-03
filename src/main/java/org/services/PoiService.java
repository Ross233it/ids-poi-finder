package org.services;
import org.httpServer.DbUtilities;
import org.models.Municipality;
import org.models.GeoLocation;
import org.models.poi.Poi;
import org.models.poi.PoiBuilder;
import org.repositories.GeoLocationRepository;
import org.repositories.MunicipalityRepository;
import org.repositories.PoiRepository;

import java.util.Map;

/**
 * Ha la responsabilità di gestire la logica di business connessa alla
 * manipolazione ed all'interazione con gli oggetti di tipo POI.
 */

public class PoiService extends Service<Poi> {

    public PoiService(PoiRepository repository) {
        super(repository);
    }

    /**
     * Ritorna un oggetto Poi e tutti i suoi dettagli a partire dall'identifcativo unico.
     * @param id identificativo unico del Poi
     * @return  Poi poi con tutti i dettagli
     * @throws Exception
     */
    @Override
    public Poi getObjectById(long id) throws Exception {
        try {
            Map<String, Object> poiData = ((PoiRepository) this.repository).getById(id, "");
            if (poiData == null) {
                return null;
            }else{
                poiData = this.buidldMunicipality(poiData);
                poiData = this.buildGeolocation(poiData);
                return this.buildEntity(poiData);
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
    public Poi create(Map<String, Object> objectData) throws Exception {
        objectData = this.buidldMunicipality(objectData);
        objectData = this.buildGeolocation(objectData);
        Poi poi = this.buildEntity(objectData);
        try {
            this.repository.create(poi);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return poi;
    }

    /**
     * Genera un oggetto Poi attraverso il builder dedicato
     * @param poiData informazioni del Poi
     * @return Poi oggetto Poi
     */
    @Override
    protected Poi buildEntity(Map<String, Object> poiData) throws Exception {
        GeoLocation geoLocation   = (GeoLocation)  poiData.get("geoLocation");
        Municipality municipality = (Municipality) poiData.get("municipality");

        Poi poi = new PoiBuilder(
                (String)  poiData.get("name"),
                (String)  poiData.get("description"),
                (Boolean) poiData.get("is_logical")).
                geoLocation(geoLocation).
                municipality(municipality).
                build();
        return poi;
    }

    /**
     * Costruisce un oggetto Municipality a partire dall'id o da informazioni testuali
     * correlate al Poi in esame
     * @param poiData dati del poi
     * @return municipality oggetto Municipality
     * @throws Exception
     */
    private Map<String, Object> buidldMunicipality(Map<String, Object> poiData) throws Exception {

        MunicipalityService municipalityService = new MunicipalityService(new MunicipalityRepository("municipalities"));
        Long municipalityId = DbUtilities.castDbIdToLong(poiData.get("municipality_id"));

        if(municipalityId>0){
            Municipality municipality = municipalityService.getObjectById(municipalityId);
            poiData.put("municipality", municipality);
        }
        return poiData;
    }

    /**
     * Costruisce un oggetto GeoLocation a partire dall'id o da informazioni testuali
     * correlate al Poi in esame
     * @param poiData dati del poi
     * @return  geoLocation oggetto GeoLocation
     * @throws Exception
     */
    private Map<String, Object> buildGeolocation(Map<String, Object> poiData) throws Exception {
        GeoLocationService geoLocationService = new GeoLocationService(new GeoLocationRepository("geolocations"));
        GeoLocation geoLocation = null;
        Map<String, Object> geoLoc = (Map<String, Object>) poiData.get("geoLocation");
        Long geoLocationId = DbUtilities.castDbIdToLong(poiData.get("geolocation_id"));
        if(geoLocationId != null){
            geoLocation = geoLocationService.getObjectById(geoLocationId);
        }else if (geoLoc != null){
            geoLocation = geoLocationService.create(geoLoc);
        }
        poiData.put("geoLocation", geoLocation);
        return poiData;
    }

    public Poi setStatus(Map<String, Object> data) throws Exception {
        Long poiId = DbUtilities.castDbIdToLong(data.get("id"));
        if(poiId > 0){
            Poi poi = this.getObjectById(poiId);
            if(poi != null){
                poi.setStatus((String) data.get("status"));
                this.repository.update(poi);
                return poi;
            }
        }
        return null;
    }
}