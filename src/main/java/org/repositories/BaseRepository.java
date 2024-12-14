package org.repositories;
import org.httpServer.DbConnectionManager;
import java.sql.Statement;

import java.sql.*;

public abstract class BaseRepository {
    protected DbConnectionManager dbConnectionManager;

    public BaseRepository() {
        this.dbConnectionManager = DbConnectionManager.getInstance();
    }

    /**
     * Ritorna il record di una tabella in base al suo id
     * @param id
     * @return
     * @throws SQLException
     */
    public ResultSet getById(int id, String tableName) throws SQLException {
        String query = "SELECT * FROM " + tableName + " WHERE id = ?";

        try (Connection connection = this.dbConnectionManager.openConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet;
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}

