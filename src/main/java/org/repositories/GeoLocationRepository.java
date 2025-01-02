package org.repositories;


import java.sql.SQLException;

import org.models.informations.GeoLocation;
import org.models.users.RegisteredUser;

public class GeoLocationRepository extends Repository<GeoLocation> {


    public GeoLocationRepository(String tableName) {
        super( "geolocations" );
    }

    @Override
    public int delete(RegisteredUser user) throws SQLException {
        return 0;
    }

    @Override
    public GeoLocation create(GeoLocation geoLoc) throws Exception {
            if (geoLoc == null) {
                throw new IllegalArgumentException("L'entity non può essere null.");
            }
            String query = "INSERT INTO "
                    + this.tableName +
                    " (address, number, cap, latitude, longitude) VALUES (?, ?, ? ,? ,?)";
            Object[] data = geoLoc.getData();
            int geoLocId = this.executeQuery(query, data);
            geoLoc.setId(geoLocId);
            return geoLoc;
    }
}
