package org.repositories;

import org.models.GeoLocation;

public class GeoLocationRepository extends Repository<GeoLocation> {


    public GeoLocationRepository() {
        super( "geolocations");
    }


    @Override
    public GeoLocation create(GeoLocation geoLoc, String query) throws Exception {
            query = "INSERT INTO "
                    + this.tableName +
                    " (address, number, cap, latitude, longitude) VALUES (?, ?, ? ,? ,?)";
            return super.create( geoLoc, query );
    }


    @Override
    public GeoLocation update(GeoLocation geolocation, String query) throws Exception {
        StringBuilder queryBuilder = new StringBuilder("UPDATE " + this.tableName)
                      .append("SET  = ?, ")
                      .append("address  = ?, ")
                      .append("number  = ?, ")
                      .append("cap  = ?, ")
                      .append("longitude  = ?, ")
                      .append("latitude  = ?, ");
        query = queryBuilder.append(" WHERE id = ?").toString();
        super.update(geolocation, query);
        return geolocation;
    }
}
