package org.repositories;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

import org.models.informations.GeoLocation;

public class GeoLocationRepository extends BaseRepository implements Repository<GeoLocation> {
    private String tableName = "geolocations";

    public GeoLocation create(GeoLocation geoLocation) {
        System.out.println("Creating GeoLocation: ");
        String query = "INSERT INTO geolocations (address, number, cap, latitude, longitude) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = this.dbConnectionManager.openConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)){
                preparedStatement.setString(1, geoLocation.getAddress());
                preparedStatement.setString(2, geoLocation.getNumber());
                preparedStatement.setString(3, geoLocation.getCap());
                preparedStatement.setDouble(4, geoLocation.getLatitude());
                preparedStatement.setDouble(5, geoLocation.getLongitude());
            int recordNum = preparedStatement.executeUpdate();

            if (recordNum > 0) {
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int generatedId = generatedKeys.getInt(1);
                        geoLocation.setId(generatedId);
                    }
                }
            }

            this.dbConnectionManager.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return geoLocation;
    }


    /**
     * Ritorna il record a db in base all'id
     * @param id l'id
     * @return ResultSet resultSet
     * @throws SQLException
     */
    public ResultSet getById(int id) throws SQLException {
        return super.getById(id, this.tableName);
    }
}
