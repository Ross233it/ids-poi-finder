package org.dataMappers;

import org.models.GeoLocation;
import org.models.municipalities.Municipality;
import org.models.poi.Poi;
import org.models.poi.PoiBuilder;
import org.models.users.RegisteredUser;

import java.util.Map;

public class PoiMapper extends DataMapper<Poi>{


    /**
     * Mappa una serie di dati in un oggetto della classe
     * @param result struttura dati contenenete informazioni sull'oggetto
     * @return istanza dell'oggetto
     */
    public Poi mapDataToObject(Map<String, Object> result){

        PoiBuilder poiBuilder =  new PoiBuilder(
                (String) result.getOrDefault("poiname", null),
                (String) result.getOrDefault("description", null),
                (Boolean) result.getOrDefault("is_logical", null));

                poiBuilder.type((String) result.getOrDefault("type", null))
                    .geoLocation((GeoLocation) result.getOrDefault("geolocation", null))
                    .municipality((Municipality) result.getOrDefault("municipality", null))
                    .author((RegisteredUser) result.getOrDefault("author", null))
                    .status((String) result.getOrDefault("status", null));

        Poi poi = (Poi) poiBuilder.build();

        if(result.containsKey("P_id") && result.get("P_id") != null){
           long id = castIdvalue(result.get("P_id"));
           poi.setId(id);
        }
        return poi;
    }
}
