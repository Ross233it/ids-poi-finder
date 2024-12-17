package org.repositories;
import org.httpServer.DbConnectionManager;
import org.models.informations.GeoLocation;

import java.sql.*;
import java.util.List;

public abstract class BaseRepository<D> implements Repository<D> {

    protected DbConnectionManager dbConnectionManager;

    protected String tableName;

    public BaseRepository(String tableName) {
        this.dbConnectionManager = DbConnectionManager.getInstance();
        this.tableName = tableName;
    }

    @Override
    public void create(List<String> columns, Object ...data ) throws Exception {
        String columnNames  = String.join(", ", columns);
        String placeholders = String.join(", ", columns.stream().map(col -> "?").toList());
        String query = "INSERT INTO " + this.tableName + " (" + columnNames + ") VALUES (" + placeholders + ");";
        this.executeQuery(query, data);
    }

    @Override
    public int delete(int id) throws SQLException {
        String query = "DELETE FROM " + this.tableName + " WHERE id = ?" ;
        Object params = new Object[]{id};
        return this.executeQuery(query, id);
    }


    /**
     * Esegue una query con statment preparato per parametri dinamici.
     * @param query
     * @param params
     * @return
     * @throws SQLException
     */
    public int executeQuery(String query, Object ...params) throws SQLException{

        Connection connection = dbConnectionManager.openConnection();
        PreparedStatement  preparedStatement = connection.prepareStatement( query, Statement.RETURN_GENERATED_KEYS );

        for (int i = 0; i < params.length; i++) {
            System.out.println(params[i].toString());
            preparedStatement.setObject(i + 1, params[i]);
        }
        preparedStatement.executeUpdate();
        ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
        if (generatedKeys.next()) {
            return generatedKeys.getInt(1);
        }
        return 0;
    }
}

