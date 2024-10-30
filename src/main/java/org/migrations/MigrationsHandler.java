package org.migrations;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.httpServer.DbConnectionManager;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class MigrationsHandler implements HttpHandler {
    private ArrayList<String> upQueries;
    private ArrayList<String> downQueries;

    public MigrationsHandler(){
        QueriesManager upQueryManager    = new UpQueriesManager();
        QueriesManager downQueryManager  = new DownQueriesManager();
        this.upQueries = (ArrayList<String>) upQueryManager.getQueries();
        this.downQueries = (ArrayList<String>) downQueryManager.getQueries();
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        DbConnectionManager dbConnectionManager = DbConnectionManager.getInstance();
        try {   Connection connection = dbConnectionManager.openConnection();
                Statement  statement  = connection.createStatement();
                for(String query : this.upQueries){
                    System.out.println("Query avviata = "+ query);
                        statement.executeUpdate(query);
                }
            dbConnectionManager.closeConnection();

        } catch (SQLException e) {
            throw new RuntimeException("Errore durante l'apertura della connessione o l'esecuzione delle query", e);
        }
    }

}
