package org.repositories;

import org.httpServer.DbConnectionManager;
import org.httpServer.DbUtilities;
import org.models.Model;

import java.sql.*;
import java.util.*;

public abstract class Repository<D extends Model> implements IRepository<D> {

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
        String data = DbUtilities.mapDbDataToJson(resultSet);
        connection.close();
        return data;
    }

    /**
     * Ritorna un elemento della tabella in base all'id
     * @param id
     * @param query
     * @return
     * @throws Exception
     */
    @Override
    public Map<String, Object> getById(int id, String query) throws Exception {
        Connection connection = dbConnectionManager.openConnection();
        if(query == null || query.isEmpty())
            query = "SELECT * FROM " + this.tableName + " WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
                          preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Map<String, Object>> data = DbUtilities.mapDbDataToList(resultSet);
        if (!data.isEmpty()) {
            Map<String, Object> userData = (Map<String, Object>) data.get(0);
            connection.close();
            return userData;
        } else
            return null;
    }

    /**
     * Salva un nuovo elemento nello strato di persistenza
     * @param  columns nomi delle colonne nella tabella
     * @param  data    valori da inserire nelle colonne
     * @throws Exception
     */
    protected void insert(List<String> columns, Object ...data) throws Exception {
        String columnNames  = String.join(", ", columns);
        String placeholders = String.join(", ", columns.stream().map(col -> "?").toList());
        String query = "INSERT INTO " + this.tableName + " (" + columnNames + ") VALUES (" + placeholders + ");";
        DbUtilities.executeQuery(query, data);
    }

    @Override
    public int delete(D entity) throws SQLException {
        int id = entity.getId();
        Object[] data = new Object[]{id};
        String query = "DELETE FROM " + this.tableName + " WHERE id = ?";
        return DbUtilities.executeQuery(query, data);
    }

    @Override
    public D update(D entity) throws SQLException {
        return entity;
    }
}

