package org.migrations;

import java.sql.SQLException;
import java.sql.Statement;

public class BaseMigration implements Migration {

    @Override
    public void migrate(String query, Statement statement) throws SQLException {
        try{
            statement.executeUpdate(query);
        } catch(Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public void up(String upQuery, Statement statement) throws SQLException {
        migrate(upQuery, statement);
    }

    public void down(String downQuery,Statement statement) throws SQLException {
        migrate(downQuery, statement);
    }
}
