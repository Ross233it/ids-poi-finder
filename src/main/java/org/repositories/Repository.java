package org.repositories;

import org.httpServer.DbConnectionManager;

import java.sql.SQLException;
import java.util.List;


/**
 * Astrae il concetto di Persistenza dei dati. Le classi che implementano
 * questa interfaccia hanno la responsabilità di gestire le interazioni fra il
 * sistema e lo strato di persistenza.
 * @param <D>
 */
public interface Repository<D> {
//    String tableName = "";
    DbConnectionManager connectionManager = null;

    public D readById(int id)       throws SQLException;
    public void create(List<String>columns, Object ...data)       throws Exception;
    public Boolean update(D entity) throws SQLException;
    public int delete(int id)   throws SQLException;

}
