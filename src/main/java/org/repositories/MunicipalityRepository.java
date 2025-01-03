package org.repositories;

import org.httpServer.DbUtilities;
import org.models.Municipality;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class MunicipalityRepository extends Repository<Municipality> {

    public MunicipalityRepository(String tableName) {
        super("municipalities");
    }


    @Override
    public Municipality create(Municipality municipality) throws Exception {
        if (municipality == null) {
            throw new IllegalArgumentException("L'entity non può essere null.");
        }
        long geolocationId = municipality.getGeoLocation().getId();
        String query = "INSERT INTO "
                + this.tableName +
                " (name, province, region, geolocation_id) VALUES (?, ?, ?, ?)";
        Object[] data = municipality.getData();
        Object[] newData = Arrays.copyOf(data, data.length + 1);
        newData[newData.length - 1] = geolocationId;
        int municipalityId = DbUtilities.executeQuery(query, newData);
        return municipality;
    }
}


