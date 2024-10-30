package org.repositories;

import org.models.Municipality;
import org.models.informations.GeoLocation;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MunicipalityRepository extends BaseRepository implements Repository{

    public void create(Municipality municipality) {
            String name = municipality.getName();
            String query = "INSERT INTO municipality (name) VALUES (?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, municipality.getName());               preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
}


