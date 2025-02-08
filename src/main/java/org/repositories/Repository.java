package org.repositories;

import org.httpServer.DbConnectionManager;
import org.httpServer.DbUtilities;
import org.models.Content;

import java.io.IOException;
import java.sql.*;
import java.util.*;

public abstract class Repository<D extends Content> implements IRepository<D> {

    protected DbConnectionManager dbConnectionManager;

    protected String tableName;

    public Repository(String tableName) {
        this.dbConnectionManager = DbConnectionManager.getInstance();
        this.tableName = tableName;
    }

    /**
     * Ritorna tutti gli elementi della tabella
     * @return
     * @throws Exception
     */
    @Override
    public List<Map<String, Object>> index(String query) throws Exception {
        if(query == null || query == ""){
            query = "SELECT * FROM "+this.tableName+";" ;
        }
        return DbUtilities.executeSelectQuery(query);
    }

    /**
     * Crea un nuovo elemento nella tabella corrente
     * @param entity l'oggetto da inserire nello strato di persistenza
     * @param query la query da eseguire per l'inserimento.
     * @return D entity l'oggetto inserito null altrimenti
     * @throws Exception
     */
    @Override
    public D create(D entity, String query) throws Exception {
        if (entity == null) {
            throw new IllegalArgumentException("L'entity non può essere null.");
        }
        Object[] data = entity.getData();
        long entityId = DbUtilities.executeQuery(query, data);
        entity.setId(entityId);
        if(entityId>0)
            return entity;
        return null;
    }

    /**
     * Aggiorna un elemento della tabella
     * @param entity l'oggetto da aggiornare
     * @param query la query da eseguire per l'aggiornamento
     * @return D entity l'oggetto aggiornato
     * @throws Exception
     */
    public D update(D entity, String query) throws Exception {
       return create(entity, query);
    }

    /**
     * Ritorna un elemento della tabella in base all'id
     * @param id long l'id dell'elemento da cercare
     * @return Map<String, Object<String,>> le informazioni dell'elemento ricercato
     * @throws Exception
     */
    @Override
    public Map<String, Object> getById(long id, String query) throws IOException, SQLException {
        if(query == null)
            query = "SELECT * FROM " + this.tableName + " WHERE id = ?";
        Object[] data = new Object[]{id};
        List<Map<String, Object>> resultSet = DbUtilities.executeSelectQuery(query, data);
        if (!resultSet.isEmpty()) {
            Map<String, Object> objectData = (Map<String, Object>) resultSet.get(0);
            return objectData;
          }
        return null;
    }

    /**
     * Ricerca un elemento in base ad una query ed un parametro di ricerca
     * @param query
     * @param searchTerm
     * @return
     * @throws Exception
     */
    @Override
    public List<Map<String, Object>> search(String query, String searchTerm) throws Exception{
        Object[] data = new Object[]{searchTerm};
        List<Map<String, Object>> resultList = DbUtilities.executeSelectQuery(query, data);
        if (!resultList.isEmpty()) {
            return resultList;
        }
        return null;
    }

    /**
     * Rimuove un set di informazioni dallo strato di peristenza
     * @param entity D entity l'oggetto di cui eliminare le informazioni
     * @return
     * @throws SQLException
     */
    @Override
    public int delete(D entity, String query) throws SQLException {
        long id = entity.getId();
        Object[] data = new Object[]{id};
        if(query == null)
            query = "DELETE FROM " + this.tableName + " WHERE id = ?";
        return DbUtilities.executeQuery(query, data);
    }
}

