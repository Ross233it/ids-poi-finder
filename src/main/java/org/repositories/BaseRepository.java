package org.repositories;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.DatabaseMetaData;

public abstract class BaseRepository {
    private final String url = "jdbc:mysql://localhost:3306/poifinder";
    private final String user = "redlabsite_233";
    private final String password = "Red#280923#Lab";

    protected Connection connection;

    /**
     * Open database connection
     * @throws SQLException
     */
    public Connection openConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Connessione aperta");
            return connection;
        }
        else
            return null;
    }

    /**
     * Close database connection
     */
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Connessione chiusa");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected boolean tableExists(Connection conn, String tableName) throws SQLException {
        DatabaseMetaData metaData = conn.getMetaData();
        try (var rs = metaData.getTables(null, null, tableName, null)) {
            return rs.next();
        }
    }
}

