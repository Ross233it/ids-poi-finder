package org.repositories;

import org.httpServer.DbUtilities;
import org.models.GeoLocation;
import org.models.municipalities.Municipality;
import org.models.poi.Poi;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Ha la responsabilità di gestire la persistenza dei dati in relazione ai Point of Interest.
 * Costruisce le query specifiche per la gestione dei Point of Interest.
 */

public class PoiRepository extends Repository<Poi> {


    public PoiRepository() {
        super("pois");
    }


    @Override
    public List<Map<String, Object>> index(String query) throws Exception {
        query = "SELECT " +
                "P.id AS poi_id, P.*, G.id AS geolocation_id, G.*, A.id as user_id, A.* " +
                "FROM pois AS P " +
                "LEFT JOIN municipalities AS M ON M.id = P.municipality_id "+
                "LEFT JOIN geolocations AS G ON G.id = M.geolocation_id "+
                "LEFT JOIN users AS A ON A.id = M.author_id;";
        return super.index(query);
    }

    @Override
    public Map<String, Object> getById(long id, String query) throws SQLException, IOException {
        query = "SELECT " +
                "P.id AS id, P.*, M.id AS municipality_id, M.*, G.id AS geolocation_id, G.*, A.id as user_id, A.*  " +
                "FROM pois AS P " +
                "LEFT JOIN municipalities AS M ON M.id = P.municipality_id "+
                "LEFT JOIN geolocations AS G ON G.id = P.geolocation_id "+
                "LEFT JOIN users AS A ON A.id = P.author_id "+
                "WHERE P.id = ?;";
        return super.getById(id,query);
    }

    @Override
    public Poi create(Poi poi, String query) throws Exception {
        query = "INSERT INTO "
                + this.tableName +
                " (poiname, description, type, status, is_logical, municipality_id, geolocation_id, author_id, approver_id) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        return super.create(poi, query);
    }


    @Override
    public Poi update(Poi poi, String query) throws Exception {
        long poiId = poi.getId();
        query = "UPDATE " + this.tableName + " " +
                "SET poiname = ?, " +
                "description = ?, " +
                "type = ?, " +
                "status = ?, " +
                "is_logical = ?, " +
                "WHERE id = ?";
        super.update(poi, query);
        return poi;
    }

    /**
     * Modifica lo stato di un punto di interesse
     * @param poi il poi di cui modificare lo stato
     * @return poi il poi modificato
     * @throws Exception
     */
    public Poi setStatus(Poi poi) throws Exception {
        if (poi == null) {
            throw new IllegalArgumentException("L'entity non può essere null.");
        }
        long poiId = poi.getId();
        String status = poi.getStatus();
        Object[] data = { status, poiId };

        String query = "UPDATE " + this.tableName + " " +
                "SET status = ? " +
                "WHERE id = ?;";
        long entityId = DbUtilities.executeQuery(query, data);
        return poi;
    };

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

    public  int delete(Poi entity)  throws SQLException {
        long id = entity.getId();
        long geoLocationId = entity.getGeoLocation().getId();
        Object[] data = new Object[]{id, geoLocationId};
        String query = "DELETE FROM " + this.tableName + " WHERE id = ?"+
                "UNION DELETE FROM geolocations WHERE id = ?";
        return DbUtilities.executeQuery(query, data);
    }


    @Override
    public List<Map<String, Object>> search(String query, String queryStringSearchTerm) throws Exception {
        query = "SELECT * FROM " + this.tableName + " AS CT " +
//                "JOIN users AS U on U.id = CT.author_id " +
                "JOIN geolocations AS G on G.id = CT.geolocation_id "+
                "WHERE name LIKE ? ;";
        queryStringSearchTerm = "%"+queryStringSearchTerm+"%";
        return super.search(query, queryStringSearchTerm);
    }



}
