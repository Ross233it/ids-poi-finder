package org.repositories;

import org.httpServer.DbConnectionManager;
import org.httpServer.DbUtilities;

import java.sql.*;
import java.util.*;

public abstract class Repository<D> implements IRepository<D> {

    protected DbConnectionManager dbConnectionManager;

    protected String tableName;

    public Repository(String tableName) {
        this.dbConnectionManager = DbConnectionManager.getInstance();
        this.tableName = tableName;
    }

    /**
     * Ritorna tutti gli elementi della tabella
     * @return
     * @throws Exception
     */
    @Override
    public String index() throws Exception {
        Connection connection = dbConnectionManager.openConnection();
        Statement statement = connection.createStatement();
        String query = "SELECT * FROM " + this.tableName;
        ResultSet resultSet = statement.executeQuery(query);

        String data = DbUtilities.mapJsonData(resultSet);
        connection.close();
        return data;
    }

    /**
     * Ritorna un elemento della tabella in base all'id con eventuali
     * join con altre tabelle.
     * @param id
     * @param query
     * @return
     * @throws Exception
     */
    @Override
    public String getById(int id, String query) throws Exception {
        Connection connection = dbConnectionManager.openConnection();
        if(query == null || query.isEmpty())
            query = "SELECT * FROM " + this.tableName + " WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        String data = DbUtilities.mapJsonData(resultSet);
        return data;
    }

    public void save(List<String> columns, Object ...data ) throws Exception {
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

    @Override
    public D update(D entity) throws SQLException {
        return entity;
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
        connection.close();
        if (generatedKeys.next()) {
            return generatedKeys.getInt(1);
        }
        return 0;
    }
}

