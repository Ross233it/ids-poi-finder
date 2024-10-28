package org.migrations;

import org.repositories.BaseRepository;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class MunicipalityMigration extends BaseRepository {

        protected void up() throws SQLException {
            Connection connection = openConnection();
            String query = "CREATE TABLE IF NOT EXISTS municipality (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "name VARCHAR(255) NOT NULL, " +
                    "geolocation_id INT, " +
                    "FOREIGN KEY (geolocation_id) REFERENCES geolocations(id) ON DELETE SET NULL" +
                    ");";

            try{
                if (!tableExists(connection, "municipality")){
                    Statement statement = connection.createStatement();
                    statement.executeUpdate(query);
                }
            } catch(Exception e) {
                    e.printStackTrace();
                    throw e;
            } finally {
                closeConnection();
            }
        }

        protected void down(){
            String query = "DROP TABLE IF EXISTS municipality";
        }
}
