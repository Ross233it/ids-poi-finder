package org.poifinder.repositories.JavaFirstVersion;

import org.poifinder.httpServer.DbConnectionManager;
import org.poifinder.httpServer.DbUtilities;
import org.poifinder.models.Content;
import org.poifinder.repositories.JavaFirstVersion.IRepository;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Classe astratta che implementa i metodi comuni a tutti i repository
 * @param <D> il tipo di oggetto che il repository gestisce
 */
public abstract class BaseRepository<D extends Content> implements IRepository<D> {

    protected DbConnectionManager dbConnectionManager;

    protected String tableName;

    StringBuilder queryBuilder;


    public BaseRepository(String tableName) {
        this.dbConnectionManager = DbConnectionManager.getInstance();
        this.tableName = tableName;
        this.queryBuilder = buildMainQuery();
    }

    /**
     * Genera un tool per la costruzione delle query select sulla base
     * di una istruzione SQL principale comune a tutte le query della classe.
     * @return queryBuilder il tool di costruzione delle query
     */
    protected StringBuilder buildMainQuery() {
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("SELECT T.*, T.id AS ");
        queryBuilder.append(tableName + "_id FROM ");
        queryBuilder.append(this.tableName);
        queryBuilder.append(" AS T");
        return queryBuilder;
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
     * @param id       long l'id dell'elemento da cercare
     * @return Map<String, Object < String, >> le informazioni dell'elemento ricercato
     * @throws Exception
     */
    @Override
    public Map<String, Object> getById(long id, String query) throws IOException, SQLException {

        if(query == null){
            queryBuilder.append(" WHERE id = ? ;");
            query = queryBuilder.toString();
        }

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
     * @return
     * @throws Exception
     */
    @Override
    public List<Map<String, Object>> search(Map<String, String> queryStringParams, String query) throws Exception{
        queryBuilder.append(" WHERE ");
        for(Map.Entry<String, String> entry : queryStringParams.entrySet()){
            queryBuilder.append(entry.getKey()).append(" LIKE \'%").append(entry.getValue()).append("%\' AND ");
        }
        queryBuilder.append(" 1 = 1;");
        query = queryBuilder.toString();
        Object[] data = new Object[]{};
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


    /**
     * Modifica lo stato di un punto di interesse
     * @param entity D l'oggetto  di cui modificare lo stato
     * @return poi il poi modificato
     * @throws Exception
     */
    public D setStatus(D entity) throws Exception {
        if (entity == null) {
            throw new IllegalArgumentException("L'entity non può essere null.");
        }
        long entityId = entity.getId();
        long approverId = entity.getApprover().getId();
        String status = entity.getStatus();

        Object[] data = { status, approverId, entityId };

        String query = "UPDATE " + this.tableName + " " +
                "SET status = ?, " +
                "approver_id = ? " +
                "WHERE id = ?;";
        long modifiedEntityId = DbUtilities.executeQuery(query, data);
        return entity;
    };
}

