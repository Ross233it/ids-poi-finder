package org.repositories;

import org.models.Municipality;

import java.util.Arrays;
import java.util.List;

public class MunicipalityRepository extends Repository<Municipality> {




    public MunicipalityRepository(String tableName) {
        super("municipalities");
    }


    @Override
    public Municipality create(Municipality municipality) throws Exception {
        if (municipality == null) {
            throw new IllegalArgumentException("L'entity non può essere null.");
        }
        int geolocationId = municipality.getGeoLocation().getId();
        List<String> columns = Arrays.asList("name", "geolocation_id");
        Object[] data = municipality.getData();
        Object[] newData = Arrays.copyOf(data, data.length + 1);
        newData[newData.length - 1] = geolocationId;
        super.save(columns, newData);
        return municipality;
    }
}


