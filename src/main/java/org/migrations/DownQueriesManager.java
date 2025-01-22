package org.migrations;

public class DownQueriesManager extends QueriesManager{

    @Override
    protected void buildQueriesList(){
        this.queries.add("DROP TABLE IF EXISTS poi_types");
        this.queries.add("DROP TABLE IF EXISTS geolocations");
        this.queries.add("DROP TABLE IF EXISTS municipalities");
        this.queries.add("DROP TABLE IF EXISTS poi_details");
        this.queries.add("DROP TABLE IF EXISTS pois");
        this.queries.add("DROP TABLE IF EXISTS users");
        this.queries.add("DROP TABLE IF EXISTS activities");
        this.queries.add("DROP TABLE IF EXISTS pois_activities");
        this.queries.add("DROP TABLE IF EXISTS contests");
        this.queries.add("DROP TABLE IF EXISTS itineraries");

    }
}
