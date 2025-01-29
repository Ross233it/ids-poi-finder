package org.repositories;

import org.models.municipalities.Municipality;

import java.util.Map;

public class MunicipalityRepository extends Repository<Municipality> {

    public MunicipalityRepository(String tableName) {
        super("municipalities");
    }

    @Override
    public Municipality create(Municipality municipality, String query) throws Exception {
        query = "INSERT INTO "
                + this.tableName +
                " (name, province, region, geolocation_id) VALUES (?, ?, ?, ?)";

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


