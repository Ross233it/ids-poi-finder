package org.migrations;

public class UpQueriesManager extends QueriesManager{

    @Override
    protected void buildQueriesList(){

        //Poi types
        this.queries.add("CREATE TABLE IF NOT EXISTS poi_types ("+
                "id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY," +
                "name VARCHAR(255) UNIQUE NOT NULL,"+
                "description TEXT," +
                "is_logical BOOLEAN NOT NULL DEFAULT 0,"+
                "categories VARCHAR(255) UNIQUE NOT NULL"+
                ");"
        );

        //Geolocations
        this.queries.add("CREATE TABLE IF NOT EXISTS  geolocations (" +
                " id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY," +
                " latitude DOUBLE NOT NULL," +
                " longitude DOUBLE NOT NULL," +
                " address VARCHAR(255) NOT NULL," +
                " number VARCHAR(20)," +
                " cap VARCHAR(10)" +
                ")"
        );

        //Municipalities
        this.queries.add("CREATE TABLE IF NOT EXISTS municipality (" +
                "id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY, " +
                "name VARCHAR(255) NOT NULL, " +
                "geolocation_id INT UNSIGNED, " +
                "FOREIGN KEY (geolocation_id) REFERENCES geolocations(id) ON DELETE CASCADE" +
                ");"
        );

        //Poi details
        this.queries.add("CREATE TABLE IF NOT EXISTS poi_details ("+
                "id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY," +
                "description TEXT," +
                "poi_type_id INT UNSIGNED," +
                "municipality_id INT UNSIGNED," +
                "geolocation_id INT UNSIGNED," +
                "FOREIGN KEY (poi_type_id) REFERENCES poi_types(id) ON DELETE SET NULL," +
                "FOREIGN KEY (municipality_id) REFERENCES municipality(id) ON DELETE CASCADE," +
                "FOREIGN KEY (geolocation_id) REFERENCES geolocations(id) ON DELETE SET NULL" +
                ")"
        );

        //Pois
        this.queries.add("CREATE TABLE IF NOT EXISTS pois ("+
                "id INT UNSIGNED PRIMARY KEY,"+
                "name VARCHAR(255) NOT NULL,"+
                "is_logical BOOLEAN NOT NULL,"+
                "poi_details_id INT UNSIGNED,"+
                "FOREIGN KEY (poi_details_id) REFERENCES poi_details(id)"+
                ");"
        );
    }


}
