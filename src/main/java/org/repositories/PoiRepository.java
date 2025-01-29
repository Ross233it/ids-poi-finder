package org.repositories;

import org.httpServer.DbUtilities;
import org.models.GeoLocation;
import org.models.poi.Poi;

import java.sql.SQLException;
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

    /**
     * Ritorna le informazioni di tutti i punti di interesse relativi ad un comune
     * @param id l'identificativo unico del comune
     * @return Map<String, Object> lista di punti di interesse
     * @throws Exception
     */
    public List<Map<String, Object>> getByMunicipalityId(Long id) throws Exception{
        String query = "SELECT * FROM " + this.tableName + " WHERE municipality_id = ?";
        Object[] data = new Object[]{id};
        List<Map<String, Object>> resultSet = DbUtilities.executeSelectQuery(query, data);
        if (resultSet.isEmpty()) {
            return null;
        }
        return resultSet;
    }
    @Override
    public  int delete(Poi entity)  throws SQLException {
        long id = entity.getId();
        long geoLocationId = entity.getGeoLocation().getId();
        Object[] data = new Object[]{id, geoLocationId};
        String query = "DELETE FROM " + this.tableName + " WHERE id = ?"+
                "UNION DELETE FROM geolocations WHERE id = ?";
        return DbUtilities.executeQuery(query, data);
    }


    @Override
    public Map<String, Object> search(String query, String queryStringSearchTerm) throws Exception {
        query = "SELECT * FROM " + this.tableName + " AS CT " +
//                "JOIN users AS U on U.id = CT.author_id " +
                "JOIN geolocations AS G on G.id = CT.geolocation_id "+
                "WHERE name LIKE ? ;";
        queryStringSearchTerm = "%"+queryStringSearchTerm+"%";
        return super.search(query, queryStringSearchTerm);
    }



}
