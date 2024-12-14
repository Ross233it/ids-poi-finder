package org.repositories;

import java.sql.SQLException;

public interface Repository<D> {
    String tableName = "";

    public D create(D entity)throws SQLException;
}
