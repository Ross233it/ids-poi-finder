package org.repositories;

import org.models.Municipality;

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
}


