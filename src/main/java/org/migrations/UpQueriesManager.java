package org.migrations;

public class UpQueriesManager extends QueriesManager{

    @Override
    protected void buildQueriesList(){

        //Poi Categories
        this.queries.add("CREATE TABLE IF NOT EXISTS categories ("+
                "id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY," +
                "name VARCHAR(255) UNIQUE NOT NULL,"+
                "description TEXT"+
                ");"
        );

        this.queries.add("CREATE TABLE IF NOT EXISTS tags ("+
                        "id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY," +
                        "name VARCHAR(255) UNIQUE NOT NULL,"+
                        "description TEXT"+
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
        //                "FOREIGN KEY (geolocation_id) REFERENCES geolocations(id) ON DELETE SET NULL" +
        this.queries.add("CREATE TABLE IF NOT EXISTS municipalities (" +
                "id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY, " +
                "name VARCHAR(255) NOT NULL, " +
                "geolocation_id INT UNSIGNED " +
                ");"
        );

        //Pois
        //                "FOREIGN KEY (municipality_id) REFERENCES municipality(id) ON DELETE CASCADE," +
//                "FOREIGN KEY (geolocation_id) REFERENCES geolocations(id) ON DELETE SET NULL" +
        this.queries.add("CREATE TABLE IF NOT EXISTS pois ("+
                "id INT UNSIGNED PRIMARY KEY,"+
                "name VARCHAR(255) NOT NULL,"+
                "description TEXT," +
                "is_logical BOOLEAN NOT NULL DEFAULT 0,"+
                "municipality_id INT UNSIGNED," +
                "geolocation_id INT UNSIGNED" +
                ");"
        );
    }


}
