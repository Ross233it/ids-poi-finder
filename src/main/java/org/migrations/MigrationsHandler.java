package org.migrations;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.sql.SQLException;

public class MigrationsHandler implements HttpHandler {
    GeoLocationMigration geoLocationMigration;
    MunicipalityMigration municipalityMigration;

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        this.geoLocationMigration = new GeoLocationMigration();
        this.municipalityMigration = new MunicipalityMigration();
        try {
            this.geoLocationMigration.up();
            this.municipalityMigration.up();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
