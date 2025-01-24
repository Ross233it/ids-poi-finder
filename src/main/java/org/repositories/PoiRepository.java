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
    public Poi create(Poi poi , String query) throws Exception {
        query = "INSERT INTO "
                + this.tableName +
                " (name, description, type, status, is_logical, municipality_id, geolocation_id) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        super.create(poi, query);
        return poi;
    }

    @Override
    public Poi update(Poi entity) throws SQLException {
        return entity;
    }
}
