package org.repositories;

import java.sql.SQLException;


/**
 * Astrae il concetto di Persistenza dei dati. Le classi che implementano
 * questa interfaccia hanno la responsabilità di gestire le interazioni fra il
 * sistema e lo strato di persistenza.
 * @param <D>
 */
public interface IRepository<D> {

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
    String  getById(int id, String query)  throws Exception;

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
    D  update(D entity) throws SQLException;

    /**
     * Cancella un elemento esistente
     * @param id
     * @return
     * @throws Exception
     */
    int     delete(int id)   throws SQLException;
}
