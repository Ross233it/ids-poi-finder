package org.repositories;

import org.models.Model;

import java.sql.SQLException;
import java.util.Map;


/**
 * Astrae il concetto di Persistenza dei dati. Le classi che implementano
 * questa interfaccia hanno la responsabilità di gestire le interazioni fra il
 * sistema e lo strato di persistenza.
 * @param <D>
 */
public interface IRepository<D extends Model> {

    /**
     * Restituisce tutti gli elementi di una tabella
     * @return
     * @throws Exception
     */
    String  index() throws Exception;

    /**
     * Restituisce un elemento di una tabella ed eventuali relazioni
     * @param id
     * @return
     * @throws Exception
     */
    Map<String, Object> getById(long id, String query)  throws Exception;

    /**
     * Crea un nuovo elemento
     * @param entity
     * @return
     * @throws Exception
     */
    D  create(D entity) throws Exception;

    /**
     * Aggiorna un elemento esistente
     * @param entity
     * @return
     * @throws Exception
     */
    D  update(D entity) throws Exception;

    /**
     * Cancella un elemento esistente
     * @param entity D entity
     * @return int numero di righe cancellate
     * @throws Exception
     */
    int  delete(D entity)   throws SQLException;
}
