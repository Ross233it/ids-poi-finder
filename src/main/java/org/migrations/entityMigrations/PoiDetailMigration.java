package org.migrations.entityMigrations;

import org.migrations.BaseMigration;

public class PoiDetailMigration extends BaseMigration {

    private String upQuery = "CREATE TABLE IF NOT EXISTS poi_details ("+
            "id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY," +
            "description TEXT," +
            "poi_type_id INT UNSIGNED," +
            "municipality_id INT UNSIGNED," +
            "geolocation_id INT UNSIGNED," +
            "FOREIGN KEY (poi_type_id) REFERENCES poi_types(id) ON DELETE SET NULL," +
            "FOREIGN KEY (municipality_id) REFERENCES municipality(id) ON DELETE CASCADE," +
            "FOREIGN KEY (geolocation_id) REFERENCES geolocations(id) ON DELETE SET NULL" +
            ")";

    private String downQuery = "DROP TABLE IF EXISTS poi_details;";


}
