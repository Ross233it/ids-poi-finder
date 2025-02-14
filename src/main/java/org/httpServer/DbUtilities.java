package org.httpServer;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DbUtilities {

    /**
     * Esegue una query di insert o update con statment preparato per parametri dinamici.
     * @param query
     * @param params
     * @return id dell'elemento inserito o modificato
     * @throws SQLException
     */
    public static int executeQuery(String query, Object... params) throws SQLException {
        DbConnectionManager dbConnectionManager = DbConnectionManager.getInstance();
        Connection connection = dbConnectionManager.openConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            for (int i = 0; i < params.length; i++) {
                preparedStatement.setObject(i + 1, params[i]);
            }

            System.out.println("Eseguendo query: " + query);
            System.out.println("Parametri: " + java.util.Arrays.toString(params));

            int rowsAffected = preparedStatement.executeUpdate();

            System.out.println("Righe interessate: " + rowsAffected);

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int generatedId = generatedKeys.getInt(1);
                    System.out.println("Chiave generata: " + generatedId);
                    return generatedId;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            System.out.println("Chiudo la connessione al database");
            connection.close();
        }
        return 1;
    }

    /**
     * Esegue una query di selezione con statment preparato per parametri dinamici.
     * @param query la query select da eseguire
     * @param params i parametri da passare alla query
     * @return List<Map<String, Object>> risultati della query
     * @throws SQLException
     */
    public static List<Map<String, Object>> executeSelectQuery(String query, Object... params) throws SQLException {
        DbConnectionManager dbConnectionManager = DbConnectionManager.getInstance();
        Connection connection = dbConnectionManager.openConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            for (int i = 0; i < params.length; i++) {
                preparedStatement.setObject(i + 1, params[i]);
            }

            System.out.println("Eseguendo query: " + query);
            System.out.println("Parametri: " + java.util.Arrays.toString(params));

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                List<Map<String, Object>> results = new ArrayList<>();

                ResultSetMetaData metaData = resultSet.getMetaData();
                int columnCount = metaData.getColumnCount();

                while (resultSet.next()) {
                    Map<String, Object> row = new HashMap<>();
                    for (int i = 1; i <= columnCount; i++) {
                        row.put(metaData.getColumnLabel(i), resultSet.getObject(i));
                    }
                    results.add(row);
                }
                System.out.println("Risultati ottenuti: " + results.size());
                return results;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            System.out.println("Chiudo la connessione al database");
            connection.close();
        }
    }
}
