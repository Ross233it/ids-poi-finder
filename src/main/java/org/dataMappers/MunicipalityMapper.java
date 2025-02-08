package org.dataMappers;

import org.models.municipalities.Municipality;
import org.models.municipalities.MunicipalityBuilder;

import java.util.Map;

public class MunicipalityMapper extends DataMapper<Municipality> {

    /**
     * Mappa una serie di dati in un oggetto della classe
     * @param result struttura dati contenenete informazioni sull'oggetto
     * @return istanza dell'oggetto
     */
    public Municipality mapDataToObject(Map<String, Object> result){
        MunicipalityBuilder builder;

        builder =  new MunicipalityBuilder(
                (String) result.getOrDefault("name", null),
                (String) result.getOrDefault("region", null),
                (String) result.getOrDefault("province", null));

        Municipality municipality =  builder.build();

        if(result.containsKey("id") && result.get("id") != null){
            long id = castIdvalue(result.get("id"));
            municipality.setId(id);
        }
        return municipality;
    }
}


