package org.migrations.entityMigrations;

import org.migrations.BaseMigration;

public class GeoLocationMigration extends BaseMigration {

    private String upQuery = "CREATE TABLE IF NOT EXISTS  geolocations (" +
            " id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY," +
            " latitude DOUBLE NOT NULL," +
            " longitude DOUBLE NOT NULL," +
            " address VARCHAR(255) NOT NULL," +
            " number VARCHAR(20)," +
            " cap VARCHAR(10)" +
            ")";

    private String downQuery = "DROP TABLE IF EXISTS geolocations";

}
