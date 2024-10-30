package org.repositories;


import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.models.informations.GeoLocation;

public class GeoLocationRepository extends BaseRepository implements Repository {

    public void create(GeoLocation geoLocation) {
        String query = "INSERT INTO geolocations (address, number, cap, latitude, longitude) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, geoLocation.getAddress());
            preparedStatement.setString(2, geoLocation.getNumber());
            preparedStatement.setString(3, geoLocation.getCap());
            preparedStatement.setDouble(4, geoLocation.getLatitude());
            preparedStatement.setDouble(5, geoLocation.getLongitude());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
