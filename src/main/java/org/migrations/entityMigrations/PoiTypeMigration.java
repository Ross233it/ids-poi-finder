package org.migrations.entityMigrations;

import org.migrations.BaseMigration;

public class PoiTypeMigration extends BaseMigration {

    private String upQuery = "CREATE TABLE IF NOT EXISTS poi_types ("+
            "id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY," +
            "name VARCHAR(255) UNIQUE NOT NULL,"+
            "description TEXT," +
            "is_logical BOOLEAN NOT NULL DEFAULT 0,"+
            "categories VARCHAR(255) UNIQUE NOT NULL"+
            ");";

    private String downQuery = "DROP TABLE IF EXISTS poi_types;";
}
