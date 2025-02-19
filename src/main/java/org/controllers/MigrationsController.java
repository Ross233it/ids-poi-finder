package org.controllers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.httpServer.DbConnectionManager;
import org.httpServer.http.HttpResponses;
import org.migrations.DownQueriesManager;
import org.migrations.QueriesManager;
import org.migrations.UpQueriesManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


/**
 * Questa classe si occupa di gestire le migrazioni del database. Si attiva tramite
 * una richiesta http
 * E' una classe di servizio per strutturare il database in modo automatico
 * senza l'ausilio di orm o framework.
 */

@RestController
@RequestMapping("api/migrations")
public class MigrationsController implements HttpHandler {


    private ArrayList<String> upQueries;

    private ArrayList<String> downQueries;



    @Autowired
    public MigrationsController(QueriesManager upQueryManager, QueriesManager downQueryManager ){
        this.upQueries = (ArrayList<String>) upQueryManager.getQueries();
        this.downQueries = (ArrayList<String>) downQueryManager.getQueries();
    }
    /**
     * Costruttore
     */
    public MigrationsController(){
        QueriesManager upQueryManager    = new UpQueriesManager();
        QueriesManager downQueryManager  = new DownQueriesManager();
        this.upQueries = (ArrayList<String>) upQueryManager.getQueries();
        this.downQueries = (ArrayList<String>) downQueryManager.getQueries();
    }

    /**
     * Questo handler si occupa di eseguire le migrazioni del database
     * @param exchange contiene la request dal client e consente di invare risposte.
     * @throws IOException
     */
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
            HttpResponses.success(exchange, "Migrations Terminate con successo");
        } catch (SQLException e) {
            System.out.println("QUERY FALLITA "+e.getMessage());
            HttpResponses.error(exchange, 500,"Si sono verificati errori nella query "+e.getMessage());
            throw new RuntimeException("Errore durante l'apertura della connessione o l'esecuzione delle query", e);
        }
    }
}
