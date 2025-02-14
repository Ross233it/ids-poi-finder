package org.repositories;

import org.models.municipalities.Municipality;
import org.models.users.RegisteredUser;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class MunicipalityRepository extends Repository<Municipality> {

    StringBuilder queryBuilder;

    public MunicipalityRepository() {
        super("municipalities");
        this.queryBuilder = buildMainQuery();
    }

    @Override
    protected StringBuilder buildMainQuery(){
        StringBuilder queryBuilder =
                new StringBuilder("SELECT ")
                        .append(" M.id AS M_id, M.*, U.*,U.id AS A_id, G.id as G_id, G.*")
                        .append("  FROM municipalities AS M ")
                        .append("LEFT JOIN geolocations AS G ON G.id = M.geolocation_id ")
                        .append("LEFT JOIN users AS U ON U.id = M.author_id ");
        return  queryBuilder;
    }

    @Override
    public List<Map<String, Object>> index(String query) throws Exception {
        return super.index(this.queryBuilder.toString());
    }

    @Override
    public Map<String, Object> getById(long id, String query) {
            query = this.queryBuilder
                    .append(  "WHERE M.id = ?;")
                    .toString();
        try {
            return super.getById(id, query);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Municipality create(Municipality municipality, String query) throws Exception {
        query = "INSERT INTO "
                + this.tableName +
                " (name, province, region, geolocation_id, author_id) VALUES (?, ?, ?, ?, ?)";
        return super.create(municipality, query);
    }

    /**
     * Rimuove dalla persistenza il comune e tutti i dati a lui
     * direttamente correlati.
     * @param municiaplity D entity
     * @return
     * @throws SQLException
     */
    @Override
    public int delete(Municipality municiaplity, String query) throws SQLException {
        query = "DELETE M, G, P "+
        "FROM municipalities AS M "+
        "LEFT JOIN geolocations AS G ON M.geolocation_id = G.id "+
        "LEFT JOIN pois AS P ON M.id = P.municipality_id " +
        "WHERE M.id = ?; ";
        return super.delete(municiaplity, query);
    }
}


