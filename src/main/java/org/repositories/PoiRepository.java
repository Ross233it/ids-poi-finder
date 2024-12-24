package org.repositories;


import org.models.poi.Poi;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

/**
 * Ha la responsabilità di gestire la persistenza dei dati in relazione ai Point of Interest.
 * Costruisce le query specifiche per la gestione dei Point of Interest.
 */

public class PoiRepository extends Repository<Poi> implements IRepository<Poi> {


    public PoiRepository(String tableName) {
        super("pois");
    }

    @Override
    public String getById(int id, String query) {
        try {
            if (query == null || query.isEmpty()){
                query = "SELECT * FROM pois AS p";
                query += " JOIN geolocations AS g ON p.geolocation_id = g.id";
                query += " WHERE p.id = ? ;" ;
            }

            return super.getById(id, query);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Poi create(Poi poi) throws Exception {
        if (poi == null) {
            throw new IllegalArgumentException("L'entity non può essere null.");
        }
        int geolocationId = poi.getGeoLocation().getId();
        List<String> columns = Arrays.asList("name", "description", "is_logical", "geolocation_id");
        Object[] data = poi.getData();
        Object[] newData = Arrays.copyOf(data, data.length + 1);
        newData[newData.length - 1] = geolocationId;
        super.save(columns, newData);
        return poi;
    }

    @Override
    public Poi update(Poi entity) throws SQLException {
        return entity;
    }

    @Override
    public int delete(int id) throws SQLException {
        return 0;
    }
}
