package org.repositories;

import org.models.Municipality;

import java.sql.*;

public class MunicipalityRepository extends BaseRepository implements Repository<Municipality>{
    private String tableName = "municipalities";

    public Municipality create(Municipality municipality) throws SQLException {
            String name = municipality.getName();
            String query = "INSERT INTO municipality (name, geolocation_id) VALUES (?, ?)";

            try (Connection connection = this.dbConnectionManager.openConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)){
                    preparedStatement.setString(1, municipality.getName());
                    preparedStatement.setInt(2, municipality.getGeoLocation().getId());
                int recordNum = preparedStatement.executeUpdate();

                if (recordNum > 0) {
                    try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            int generatedId = generatedKeys.getInt(1);
                            municipality.setId(generatedId);
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return municipality;
        }

    /**
     * Ritorna il record a db in base all'id
     * @param id l'id
     * @return ResultSet resultSet
     * @throws SQLException
     */
    public ResultSet getById(Integer id) throws SQLException {
            return super.getById(id, this.tableName);
    }
}


