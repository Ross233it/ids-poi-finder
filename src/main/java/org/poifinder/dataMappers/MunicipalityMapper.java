package org.poifinder.dataMappers;

import org.poifinder.models.municipalities.Municipality;
import org.poifinder.models.municipalities.MunicipalityBuilder;

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

        if(result.containsKey("M_id") && result.get("M_id") != null){
            long id = castIdvalue(result.get("M_id"));
            municipality.setId(id);
        }
        return municipality;
    }
}


