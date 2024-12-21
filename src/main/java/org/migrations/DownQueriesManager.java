package org.migrations;

public class DownQueriesManager extends QueriesManager{

    @Override
    protected void buildQueriesList(){
        this.queries.add("DROP TABLE IF EXISTS poi_types");
        this.queries.add("DROP TABLE IF EXISTS geolocations");
        this.queries.add("DROP TABLE IF EXISTS municipality");
        this.queries.add("DROP TABLE IF EXISTS poi_details");
        this.queries.add("DROP TABLE IF EXISTS pois");
        this.queries.add("DROP TABLE IF EXISTS users");
    }
}
