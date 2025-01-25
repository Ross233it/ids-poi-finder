package org.repositories;

import org.models.poi.Poi;

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
    public Poi update(Poi poi, String query) throws Exception {
        long poiId = poi.getId();
        query = "UPDATE " + this.tableName + " " +
                "SET name = ?, " +
                "description = ?, " +
                "type = ?, " +
                "status = ?, " +
                "is_logical = ?, " +
                "WHERE id = ?";
        super.update(poi, query);
        return poi;
    }
}
