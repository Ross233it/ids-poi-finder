package org.repositories;

import org.httpServer.DbConnectionManager;
import org.httpServer.DbUtilities;
import org.models.users.IUser;
import org.models.users.RegisteredUser;

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

    @Override
    public int delete(D entity) throws SQLException {
        return 0;
    }

    @Override
    public D update(D entity) throws Exception {
        return entity;
    }

    /**
     * Esegue una query con statment preparato per parametri dinamici.
     * @param query
     * @param params
     * @return id dell'elemento inserito o modificato
     * @throws SQLException
     */
    protected int executeQuery(String query, Object ...params) throws SQLException{
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
        int value = 0;
        connection.close();
        return value;
    }

    /**
     * Salva un nuovo elemento nella tabella
     *
     * @param columns nomi delle colonne nella tabella
     * @param data    valori da inserire nelle colonne
     * @throws Exception
     */
    protected void save(List<String> columns, Object ...data) throws Exception {
        String columnNames  = String.join(", ", columns);
        String placeholders = String.join(", ", columns.stream().map(col -> "?").toList());
        String query = "INSERT INTO " + this.tableName + " (" + columnNames + ") VALUES (" + placeholders + ");";
        this.executeQuery(query, data);
    }


    public abstract int delete(RegisteredUser user) throws SQLException;
}

