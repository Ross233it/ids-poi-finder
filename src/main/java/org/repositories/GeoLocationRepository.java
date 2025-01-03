package org.repositories;


import org.httpServer.DbUtilities;
import org.models.GeoLocation;

public class GeoLocationRepository extends Repository<GeoLocation> {


    public GeoLocationRepository(String tableName) {
        super( "geolocations" );
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
            int geoLocId = DbUtilities.executeQuery(query, data);
            geoLoc.setId(geoLocId);
            return geoLoc;
    }
}
