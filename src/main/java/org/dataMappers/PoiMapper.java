package org.dataMappers;

import org.models.poi.Poi;
import org.models.poi.PoiBuilder;

import java.util.Map;

public class PoiMapper extends DataMapper<Poi>{


    /**
     * Mappa una serie di dati in un oggetto della classe
     * @param result struttura dati contenenete informazioni sull'oggetto
     * @return istanza dell'oggetto
     */
    public Poi mapDataToObject(Map<String, Object> result){
        PoiBuilder builder;

        builder =  new PoiBuilder(
                (String) result.getOrDefault("poiname", null),
                (String) result.getOrDefault("description", null),
                (Boolean) result.getOrDefault("is_logical", null));
        Poi poi =  builder.build();
        if(result.containsKey("id") && result.get("id") != null){
           long id = castIdvalue(result.get("id"));
           poi.setId(id);
        }
        return poi;
    }

//    protected  long castIdvalue(Object idValue){
//        long id = 0L;
//
//        if (idValue instanceof Integer) {
//            id = ((Integer) idValue).longValue();
//        } else if (idValue instanceof Long) {
//            id = (Long) idValue;
//        } else if (idValue instanceof String) {
//            try {
//                id = Long.parseLong((String) idValue);
//            } catch (NumberFormatException e) {
//                System.err.println("Errore di conversione String -> Long: " + idValue);
//            }
//        }
//        return id;
//    }

}
