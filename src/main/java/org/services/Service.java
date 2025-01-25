package org.services;

import org.models.Content;
import org.models.users.RegisteredUser;
import org.repositories.Repository;

import java.util.Map;

/**
 * Ha la responsabilità di gestire la logica di business connessa alla
 * manipolazione ed all'interazione con gli oggetti di tipo POI.
 */

public class Service<D extends Content> implements IService<D> {

    Repository repository;

    public Service(Repository repository) {
        this.repository = repository;
    }

    public String index() {
        try {
            return this.repository.index();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Crea un nuovo poi partendo da una serie di dati già validati
     * @param objectData
     * @return
     */
    public D create(Map<String, Object> objectData) throws Exception {
        D entity = this.buildEntity(objectData);
        try {
            this.repository.create(entity, "");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return entity;
    }

    /**
     * Crea un nuovo poi partendo da una serie di dati già validati
     * @param objectData
     * @return
     */
    public D create(Map<String, Object> objectData, RegisteredUser author) throws Exception {
        if(author == null)
            throw new IllegalArgumentException("L'autore non può essere null");
        D entity = this.buildEntity(objectData);
        entity.setAuthor(author);
        try {
            this.repository.create(entity, "");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return entity;
    }

    /**
     * Aggiorna un oggetto partendo da una serie di dati già validati
     * @param objectData
     * @return
     */
    @Override
    public D update(long id, Map<String, Object> objectData) throws Exception {
        D entity = this.buildEntity(objectData);
        entity.setId(id);
        try {
            this.repository.update(entity, "");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return entity;
    }


    /**
     * Crea un oggetto sulla base delle informazioni recuperate dallo strato di persistenza.
     * @param query il termine di ricerca per la query
     * @return D l'oggetto creato | null altrimenti.
     * @throws Exception
     */
    public D search(String query) throws Exception {
        Map<String, Object> entityData = this.repository.search("", query);
        if(entityData == null)
            return null;
        D entity = this.buildEntity(entityData);
        return entity;
    }
    /**
     * Ritorna un oggetto in base all'id e ai dati recuperati dallo strato di persistenza.
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public D getObjectById(long id) throws Exception {
        Map<String, Object> entityData =  this.repository.getById(id, "");
        if(entityData == null)
            return null;
        D entity = this.buildEntity(entityData);
        entity.setId(id);
        return entity;
    }

    @Override
    public D delete(long id) throws Exception {
        D entity = this.getObjectById(id);
        if(entity == null)
            return null;
        int result = this.repository.delete(entity);
        if(result > 0)
            return entity;
        else
            return null;
    }



    protected D buildEntity(Map<String, Object> objectData)throws Exception
    {
        return null;
    }

}
