package org.repositories;


import org.httpServer.DbUtilities;
import org.models.GeoLocation;

public class GeoLocationRepository extends Repository<GeoLocation> {


    public GeoLocationRepository(String tableName) {
        super( "geolocations" );
    }

    @Override
    public GeoLocation create(GeoLocation geoLoc, String query) throws Exception {
            query = "INSERT INTO "
                    + this.tableName +
                    " (address, number, cap, latitude, longitude) VALUES (?, ?, ? ,? ,?)";
            return super.create( geoLoc, query );
    }
}
