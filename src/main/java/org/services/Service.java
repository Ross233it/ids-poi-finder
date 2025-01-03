package org.services;

import org.models.Model;
import org.repositories.Repository;

import java.util.Map;

/**
 * Ha la responsabilità di gestire la logica di business connessa alla
 * manipolazione ed all'interazione con gli oggetti di tipo POI.
 */

public class Service<D extends Model> implements IService<D> {

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
            this.repository.create(entity);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return entity;
    }

    /**
     * Ritorna un oggetto in base all'id e ai dati recuperati dallo strato di persistenza.
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public D getObjectById(int id) throws Exception {
        Map<String, Object> entityData =  this.repository.getById(id, "");
        if(entityData == null)
            return null;
        D entity = this.buildEntity(entityData);
        entity.setId(id);
        return entity;
    }

    @Override
    public D delete(int id) throws Exception {
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
