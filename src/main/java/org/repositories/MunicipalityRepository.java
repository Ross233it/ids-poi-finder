package org.repositories;


import org.models.municipalities.Municipality;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class MunicipalityRepository extends Repository<Municipality> {



    public MunicipalityRepository() {
        super("municipalities");
    }

    @Override
    public List<Map<String, Object>> index(String query) throws Exception {
        query = "SELECT * FROM poifinder.municipalities AS M "+
                "LEFT JOIN geolocations AS G ON G.id = M.geolocation_id "+
                "LEFT JOIN users AS A ON A.id = M.author_id;";
        return super.index(query);
    }


    @Override
    public Map<String, Object> getById(long id, String query) throws SQLException, IOException {
         query = "SELECT * FROM poifinder.municipalities AS M "+
                 "LEFT JOIN geolocations AS G ON G.id = M.geolocation_id "+
                 "LEFT JOIN users AS A ON A.id = M.author_id "+
                 "WHERE M.id = ?;";
         return super.getById(id,query);
    }


    @Override
    public Municipality create(Municipality municipality, String query) throws Exception {
        query = "INSERT INTO "
                + this.tableName +
                " (name, province, region, geolocation_id, author_id) VALUES (?, ?, ?, ?, ?)";
        return super.create(municipality, query);
    }


    @Override
    public Map<String, Object> search(String query, String queryStringSearchTerm) throws Exception {
        query = "SELECT * FROM " + this.tableName + " AS CT " +
                "JOIN users AS U on U.id = CT.author_id " +
                "JOIN geolocations AS G on G.id = CT.geolocation_id "+
                "WHERE name LIKE ? ;";
        queryStringSearchTerm = "%"+queryStringSearchTerm+"%";
        return super.search(query, queryStringSearchTerm);
    }
}


