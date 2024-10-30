package org.migrations.entityMigrations;

import org.migrations.BaseMigration;

public class PoiMigration extends BaseMigration {

    private String upQuery = "CREATE TABLE IF NOT EXISTS pois ("+
            "id INT UNSIGNED PRIMARY KEY,"+
            "name VARCHAR(255) NOT NULL,"+
            "is_logical BOOLEAN NOT NULL,"+
            "poi_details_id INT UNSIGNED,"+
            "FOREIGN KEY (poi_details_id) REFERENCES poi_details(id)"+
            ");";

    private String downQuery = "DROP TABLE IF EXISTS pois";


}
