package org.repositories;

import org.httpServer.DbConnectionManager;
import org.httpServer.DbUtilities;
import org.models.Content;

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
    public String index() throws Exception {
        Connection connection = dbConnectionManager.openConnection();
        Statement statement = connection.createStatement();
        String query = "SELECT * FROM " + this.tableName;
        ResultSet resultSet = statement.executeQuery(query);
        String data = DbUtilities.mapDbDataToJson(resultSet);
        connection.close();
        return data;
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
       return this.create(entity, query);
    }

    /**
     * Ritorna un elemento della tabella in base all'id
     * @param id long l'id dell'elemento da cercare
     * @param query string la query di ricerca
     * @return Map<String, Object<String,>> le informazioni dell'elemento ricercato
     * @throws Exception
     */
    @Override
    public Map<String, Object> getById(long id, String query) throws Exception {
        Connection connection = dbConnectionManager.openConnection();
        if(query == null || query.isEmpty())
            query = "SELECT * FROM " + this.tableName + " WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
                          preparedStatement.setLong(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Map<String, Object>> data = DbUtilities.mapDbDataToList(resultSet);
        if (!data.isEmpty()) {
            Map<String, Object> objectData = (Map<String, Object>) data.get(0);
            connection.close();
            return objectData;
        } else
            return null;
    }

    /**
     * Ricerca un elemento in base ad una query ed un parametro di ricerca
     * @param query
     * @param searchTerm
     * @return
     * @throws Exception
     */
    public Map<String, Object> search(String query, String searchTerm) throws Exception{
        Object[] data = new Object[]{searchTerm};
        List<Map<String, Object>> resultSet = DbUtilities.executeSelectQuery(query, data);
        System.out.println("RISULTATO DELLA RICERCA: " + resultSet);
        if (!resultSet.isEmpty()) {
            Map<String, Object> objectData = (Map<String, Object>) resultSet.get(0);
            return objectData;
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
    public int delete(D entity) throws SQLException {
        long id = entity.getId();
        Object[] data = new Object[]{id};
        String query = "DELETE FROM " + this.tableName + " WHERE id = ?";
        return DbUtilities.executeQuery(query, data);
    }

    @Override
    public D update(D entity) throws SQLException {
        return entity;
    }
}

