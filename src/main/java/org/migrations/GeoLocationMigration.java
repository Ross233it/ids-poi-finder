package org.migrations;

import org.repositories.BaseRepository;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class GeoLocationMigration extends BaseRepository {

    protected void up() throws SQLException {
        Connection connection = openConnection();

        String query = "CREATE TABLE IF NOT EXISTS  geolocations (" +
        " id INT AUTO_INCREMENT PRIMARY KEY," +
        " latitude DOUBLE NOT NULL," +
        " longitude DOUBLE NOT NULL," +
        " address VARCHAR(255) NOT NULL," +
        " number VARCHAR(20)," +
        " cap VARCHAR(10)" +
        ")";
        try{
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
        } catch(Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            closeConnection();
        }
    }

    protected void down(){
        String query = "DROP TABLE geolocations";
    }
}
