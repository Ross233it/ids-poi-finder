package org.poifinder.repositories;

import org.poifinder.models.GeoLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GeoLocationRepository extends JpaRepository<GeoLocation, Long> {


//    public GeoLocationRepository() {
//        super( "geolocations");
//    }
//
//
//    @Override
//    public GeoLocation create(GeoLocation geoLoc, String query) throws Exception {
//            query = "INSERT INTO "
//                    + this.tableName +
//                    " (address, number, cap, latitude, longitude) VALUES (?, ?, ? ,? ,?)";
//            return super.create( geoLoc, query );
//    }


//    @Override
//    public GeoLocation update(GeoLocation geolocation, String query) throws Exception {
//        Object[] data = geolocation.getData();
//        StringBuilder queryBuilder = new StringBuilder("UPDATE " + this.tableName)
//                      .append(" SET ")
//                      .append(" address  = ?, ")
//                      .append(" number  = ?, ")
//                      .append(" cap  = ?, ")
//                      .append(" longitude  = ?, ")
//                      .append(" latitude  = ? ");
//        query = queryBuilder.append(" WHERE id = "+ geolocation.getId()).toString();
//        DbUtilities.executeQuery(query, data);
//        return geolocation;
//    }
}
