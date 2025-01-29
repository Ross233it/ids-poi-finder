package org.services;

import org.httpServer.HttpResponses;
import org.models.municipalities.Municipality;
import org.models.GeoLocation;
import org.models.poi.Poi;
import org.models.poi.PoiBuilder;
import org.models.users.RegisteredUser;
import org.repositories.GeoLocationRepository;
import org.repositories.MunicipalityRepository;
import org.repositories.PoiRepository;

import java.util.ArrayList;
import java.util.List;
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
            Map<String, Object> poiData = ((PoiRepository) this.repository).getById(id, null);
            if (poiData == null) {
                return null;
            }else{
                poiData = this.addMunicipality(poiData);
                poiData = this.addGeolocation(poiData);
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
    public Poi create(Map<String, Object> objectData, RegisteredUser author) throws Exception {
        objectData = this.addMunicipality(objectData);
        objectData = this.addGeolocation(objectData);
        System.out.println("DATI: " + objectData);
        Poi poi = this.buildEntity(objectData);
        try {
            this.repository.create(poi, "");
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
    private Map<String, Object> addMunicipality(Map<String, Object> poiData) throws Exception {

//        Integer municipalityId = (Integer) poiData.get("municipality_id");
        Integer municipalityId = ((Long) poiData.get("municipality_id")).intValue();
        MunicipalityService municipalityService = new MunicipalityService(new MunicipalityRepository("municipalities"));

        if(municipalityId>0){
            Municipality municipality = municipalityService.getObjectById(municipalityId);
            poiData.put("municipality", municipality);
        }
        else
            return null;
        return poiData;
    }

    /**
     * Costruisce un oggetto GeoLocation a partire dall'id o da informazioni testuali
     * correlate al Poi in esame
     * @param poiData dati del poi
     * @return  geoLocation oggetto GeoLocation
     * @throws Exception
     */
    private Map<String, Object> addGeolocation(Map<String, Object> poiData) throws Exception {
        GeoLocationService geoLocationService = new GeoLocationService(new GeoLocationRepository("geolocations"));

        Map<String, Object> geoLoc = (Map<String, Object>) poiData.get("geoLocation");

        GeoLocation geoLocation = geoLocationService.create(geoLoc);
        geoLoc.put("id", geoLocation.getId());
        poiData.put("geoLocation", geoLocation);
        return poiData;
    }

    /**
     * Aggiorna lo stato di un Poi
     * @param data i dati del Poi
     * @return Poi poi con il nuovo stato
     * @throws Exception
     */
    public Poi setStatus(Map<String, Object> data) throws Exception {
        Long poiId = (Long) data.get("id");
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




    /**
     * Ritorna tutti i Poi di un determinato comune
     * @param id l'id del comune di interesse
     * @return ArrayList<Poi> lista di Poi
     */
    public ArrayList<Poi> getByMunicipalityId(Long id) throws Exception {
        ArrayList<Poi> pois = new ArrayList<>();
        List<Map<String, Object>> data = ((PoiRepository) this.repository).getByMunicipalityId(id);
        for (Map<String, Object> poiData : data) {
            try {
//                poiData = this.addMunicipality(poiData);
//                poiData = this.addGeolocation(poiData);
                Poi poi = this.buildEntity(poiData);
                poi.setMunicipality(null);
                poi.setGeoLocation(null);
                pois.add(poi);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return pois;
    }


    /**
     * Stampa un oggetto Poi in formato Json
     * @param poi
     * @return
     */
    public String printPoi(Poi poi) throws IllegalAccessException {
        String poiData = HttpResponses.objectToJson(poi);
        String geolocationData  =  HttpResponses.objectToJson(poi.getGeoLocation());
        String municipalityData  = HttpResponses.objectToJson(poi.getMunicipality());
        String combinedJson = "{"
                + poiData
                + ", \"geoLocation\": "  + geolocationData
                + ", \"municipality\": " + municipalityData
                +"}";
        return combinedJson;
    }
}