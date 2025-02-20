package org.poifinder.repositories;

import org.poifinder.httpServer.DbUtilities;
import org.poifinder.models.municipalities.Municipality;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Repository
public class MunicipalityRepository extends BaseRepository<Municipality> {


//    public MunicipalityRepository() {
//        super("municipalities");
//        this.queryBuilder = buildMainQuery();
//    }

//    @Override
//    public List<Map<String, Object>> index(String query) throws Exception {
//        return super.index(this.queryBuilder.toString());
//    }
//
//    @Override
//    public Map<String, Object> getById(long id, String query) {
//            query = this.queryBuilder
//                    .append(  "WHERE M.id = ?;")
//                    .toString();
//        try {
//            return super.getById(id, query);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Override
//    public Municipality create(Municipality municipality, String query) throws Exception {
//        query = "INSERT INTO "
//                + this.tableName +
//                " (name, province, region, geolocation_id, author_id) VALUES (?, ?, ?, ?, ?)";
//        return super.create(municipality, query);
//    }
//
//    @Override
//    public Municipality update(Municipality municipality, String query) throws Exception {
//        Object[] data = municipality.getData();
//
//        query = "UPDATE " + this.tableName + " " +
//                " SET " +
//                " name = ?, " +
//                " region = ?, " +
//                " province = ?, " +
//                " geolocation_id = ?," +
//                " author_id = ? " +
//                " WHERE id = "+ municipality.getId() +";";
//        DbUtilities.executeQuery(query, data);
//        return municipality;
//    }
//
//    /**
//     * Rimuove dalla persistenza il comune e tutti i dati a lui
//     * direttamente correlati.
//     * @param municiaplity D entity
//     * @return
//     * @throws SQLException
//     */
//    @Override
//    public int delete(Municipality municiaplity, String query) throws SQLException {
//        query = "DELETE M, G, P "+
//        "FROM municipalities AS M "+
//        "LEFT JOIN geolocations AS G ON M.geolocation_id = G.id "+
//        "LEFT JOIN pois AS P ON M.id = P.municipality_id " +
//        "WHERE M.id = ?; ";
//        return super.delete(municiaplity, query);
//    }
}


