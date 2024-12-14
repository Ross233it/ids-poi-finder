package org.httpServer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/** Singleton pattern **/

public class DbConnectionManager {
    private final String url = "jdbc:mysql://localhost:3306/poifinder";
    private final String user = "redlabsite_233";
    private final String password = "Red#280923#Lab";

    private static DbConnectionManager instance = null;
    private Connection connection;

    private DbConnectionManager() {}

    /**
     * Restituisce l'istanza singleton di DbConnectionManager.
     * @return Istanza di DbConnectionManager
     */
    public static DbConnectionManager getInstance() {
        if (instance == null) {
            instance = new DbConnectionManager();
        }
        return instance;
    }

    /**
     * Apre la connessione al database
     * @return Connection
     * @throws SQLException
     */
    public Connection openConnection() throws SQLException {
        if (this.connection == null || this.connection.isClosed()) {
            this.connection = DriverManager.getConnection(url, user, password);
            System.out.println("Connessione db aperta");
        }
        return this.connection;
    }

    /**
     * Chiude la connessione al database
     */
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Connessione db chiusa");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * Verifica la connessione al database
     * @return true se la connessione è attiva, false altrimenti
     */
    public boolean dbCheckConnection() {
        try {
            openConnection();
            return connection != null && !connection.isClosed();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
