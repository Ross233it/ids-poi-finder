package org.repositories;

import org.models.poi.Poi;
import org.models.users.RegisteredUser;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Ha la responsabilità di gestire la persistenza dei dati in relazione ai Point of Interest.
 * Costruisce le query specifiche per la gestione dei Point of Interest.
 */

public class PoiRepository extends Repository<Poi> {

    public PoiRepository(String tableName) {
        super("pois");
    }

    @Override
    public Poi create(Poi poi) throws Exception {
        if (poi == null) {
            throw new IllegalArgumentException("L'entity non può essere null.");
        }
        long geolocationId  = poi.getGeoLocation().getId();
        long municipalityId = poi.getMunicipality().getId();

        List<String> columns = Arrays.asList("name", "description", "is_logical", "status", "geolocation_id", "municipality_id");
        Object[] data = poi.getData();
        Object[] newData = Arrays.copyOf(data, data.length + 2);
        newData[newData.length - 2] = geolocationId;
        newData[newData.length - 1] = municipalityId;
        super.insert(columns, newData);
        return poi;
    }

    @Override
    public Poi update(Poi entity) throws SQLException {
        return entity;
    }
}
