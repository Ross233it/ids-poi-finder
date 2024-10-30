package org.repositories;
import org.httpServer.DbConnectionManager;
import java.sql.Statement;

import java.sql.*;

public abstract class BaseRepository {
    protected DbConnectionManager dbConnectionManager;

    public BaseRepository() {
        this.dbConnectionManager = DbConnectionManager.getInstance();
    }

//    public void executePreparedQuery(String query, PreparedStatement preparedStatement){
//        try {
//
//            dbConnectionManager.closeConnection();
//
//        } catch (SQLException e) {
//            throw new RuntimeException("Errore durante l'apertura della connessione o l'esecuzione delle query", e);
//        }
//    }


}

