package org.repositories;

import org.httpServer.DbUtilities;
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

    StringBuilder queryBuilder;

    Map<String, String>columnNames;

    public PoiRepository() {
        super("pois");
        this.queryBuilder = this.buildMainQuery();
    }

    @Override
    protected StringBuilder buildMainQuery(){
        StringBuilder queryBuilder =
            new StringBuilder("SELECT P.*, P.id AS P_id, G.*, G.id AS G_id, U.*, U.id AS A_id")
            .append(", M.*, M.id AS M_id ")
            .append("  FROM pois AS P ")
            .append("LEFT JOIN geolocations AS G ON G.id = P.geolocation_id ")
            .append("LEFT JOIN users AS U ON U.id = P.author_id ")
            .append("LEFT JOIN municipalities AS M ON M.id = P.municipality_id ");
        return  queryBuilder;
    }

    @Override
    public List<Map<String, Object>> index(String query) throws Exception {
        return super.index(this.queryBuilder.toString());
    }

    @Override
    public Map<String, Object> getById(long id, String query) throws SQLException, IOException {
        int fromIndex = queryBuilder.indexOf("FROM");
        if (fromIndex != -1) {
            this.queryBuilder.insert(fromIndex, ",M.* ");       }

        query = this.queryBuilder
                .append("WHERE P.id = ?;")
                .toString();
        return super.getById(id, query);
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

//    /**
//     * Modifica lo stato di un punto di interesse
//     * @param poi il poi di cui modificare lo stato
//     * @return poi il poi modificato
//     * @throws Exception
//     */
//    public Poi setStatus(Poi poi) throws Exception {
//        if (poi == null) {
//            throw new IllegalArgumentException("L'entity non può essere null.");
//        }
//        long poiId = poi.getId();
//        String status = poi.getStatus();
//        Object[] data = { status, poiId };
//
//        String query = "UPDATE " + this.tableName + " " +
//                "SET status = ? " +
//                "WHERE id = ?;";
//        long entityId = DbUtilities.executeQuery(query, data);
//        return poi;
//    };

    /**
     * Ritorna le informazioni di tutti i punti di interesse relativi ad un comune
     * @param id l'identificativo unico del comune
     * @return Map<String, Object> lista di punti di interesse
     * @throws Exception
     */
    public List<Map<String, Object>> getByMunicipalityId(Long id) throws Exception{
        String query = this.queryBuilder
                .append("WHERE P.municipality_id = ?;")
                .toString();

        Object[] data = new Object[]{id};
        List<Map<String, Object>> resultSet = DbUtilities.executeSelectQuery(query, data);
        if (resultSet.isEmpty()) {
            return null;
        }
        return resultSet;
    }

    @Override
    public  int delete(Poi entity, String query)  throws SQLException {
        long id = entity.getId();
        Object[] data = new Object[]{id};

        query =
        "DELETE P, G "+
        "FROM pois AS P "+
        "LEFT JOIN geolocations AS G ON G.id = P.geolocation_id "+
        "WHERE P.id = ?; " ;
        return DbUtilities.executeQuery(query, data);
    }
}
