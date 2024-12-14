package org.controllers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.httpServer.DbConnectionManager;
import org.httpServer.HttpResponses;
import org.migrations.DownQueriesManager;
import org.migrations.QueriesManager;
import org.migrations.UpQueriesManager;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class MigrationsController implements HttpHandler {

    private ArrayList<String> upQueries;

    private ArrayList<String> downQueries;

    private final HttpResponses httpResponses;

    /**
     * Costruttore
     */
    public MigrationsController(){
        QueriesManager upQueryManager    = new UpQueriesManager();
        QueriesManager downQueryManager  = new DownQueriesManager();
        this.upQueries = (ArrayList<String>) upQueryManager.getQueries();
        this.downQueries = (ArrayList<String>) downQueryManager.getQueries();
        this.httpResponses = new HttpResponses();
    }


    public void handle(HttpExchange exchange) throws IOException {
        DbConnectionManager dbConnectionManager = DbConnectionManager.getInstance();

        try {   Connection connection = dbConnectionManager.openConnection();
                Statement  statement  = connection.createStatement();
                for(String query : this.downQueries){
                    System.out.println("Query avviata = "+ query);
                    statement.executeUpdate(query);
                }

                for(String query : this.upQueries){
                    System.out.println("Query avviata = "+ query);
                        statement.executeUpdate(query);
                }
            dbConnectionManager.closeConnection();
            System.out.println("MIGRATIONS TERMINATE CON SUCCESSO");
            httpResponses.success(exchange, "Migrations Terminate con successo");
        } catch (SQLException e) {
            System.out.println("QUERY FALLITA "+e.getMessage());
            httpResponses.error(exchange, 500,"Si sono verificati errori nella query "+e.getMessage());
            throw new RuntimeException("Errore durante l'apertura della connessione o l'esecuzione delle query", e);
        }
    }

}
