package org.repositories;


import java.sql.SQLException;

import org.models.informations.GeoLocation;

public class GeoLocationRepository extends BaseRepository<GeoLocation> {


    public GeoLocationRepository(String tableName) {
        super( tableName );
    }

//    @Override
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

    @Override
    public GeoLocation readById(int id) throws SQLException {
        return null;
    }


    @Override
    public Boolean update(GeoLocation entity) throws SQLException {
        return null;
    }

    @Override
    public int delete(int id) throws SQLException {
        return 0;
    }
}
