package org.migrations;

import java.sql.SQLException;
import java.sql.Statement;

/**
 * Astrae le operazioni di creazione modifica ed eliminazione
 * sulle tabelle di database relazionale.
 **/

public interface Migration {


    public void migrate(String query, Statement statement) throws SQLException;

    public void up(String query, Statement statement) throws SQLException;

    public void down(String query, Statement statement) throws SQLException;
}
