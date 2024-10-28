package org.repositories;


import org.migrations.MigrationsHandler;
import org.models.informations.GeoLocation;

public class GeoLocationRepository extends BaseRepository implements Repository {

    MigrationsHandler migrationsHandler;

    public void create(GeoLocation geoLocation) {
        String address   = geoLocation.getAddress();
        String number    = geoLocation.getNumber();
        String cap       = geoLocation.getCap();
        Double latitude  = geoLocation.getLatitude();
        Double longitude = geoLocation.getLongitude();
    }

//    private int getLastId(){
//      String query = "SELECT MAX(ID) FROM geoLocations";
//
//
//    };
}
