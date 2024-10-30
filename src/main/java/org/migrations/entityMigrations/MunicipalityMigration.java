package org.migrations.entityMigrations;

import org.migrations.BaseMigration;

import java.sql.SQLException;

public class MunicipalityMigration extends BaseMigration {

        private  String upQuery = "CREATE TABLE IF NOT EXISTS municipality (" +
                "id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY, " +
                "name VARCHAR(255) NOT NULL, " +
                "geolocation_id INT UNSIGNED, " +
                "FOREIGN KEY (geolocation_id) REFERENCES geolocations(id) ON DELETE CASCADE" +
                ");";

        private String downQuery = "DROP TABLE IF EXISTS municipality";
}
