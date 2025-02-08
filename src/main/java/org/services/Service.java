package org.services;

import org.dataMappers.DataMapper;
import org.eventManager.EmailNotifier;
import org.eventManager.EventManager;
import org.eventManager.LogNotifier;
import org.models.Content;
import org.models.users.RegisteredUser;
import org.repositories.Repository;

import java.util.EventListener;
import java.util.List;
import java.util.Map;

/**
 * Ha la responsabilità di gestire la logica di business connessa alla
 * manipolazione ed all'interazione con gli oggetti di tipo POI.
 */

public class Service<D extends Content> implements IService<D> {

    protected Repository repository;

    protected DataMapper mapper;

    protected EventManager eventManager;

    public Service(Repository repository, DataMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
        this.eventManager = new EventManager();
        eventManager.subscribe(new EmailNotifier());
        eventManager.subscribe(new LogNotifier());
    }

    public List<D> index() {
        try {
            List<Map<String, Object>>results = this.repository.index(null);
            List<D> objectList = this.mapper.mapDbDataToObjects(results);
            eventManager.notify("INDEX CALL");
            return objectList;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Crea un oggetto sulla base delle informazioni recuperate dallo strato di persistenza.
     * @param query il termine di ricerca per la query
     * @return D l'oggetto creato | null altrimenti.
     * @throws Exception
     */
    public List<D> search(String query) throws Exception {
        List<Map<String, Object>> entityData = this.repository.search("", query);
        if(entityData == null)
            return null;
        List<D> entities = (List<D>) mapper.mapDbDataToObjects(entityData);
        return entities;
    }

    /**
     * Ritorna un oggetto in base all'id e ai dati recuperati dallo strato di persistenza.
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public D getObjectById(long id) throws Exception {
        Map<String, Object> entityData =  this.repository.getById(id, null);
        if(entityData == null)
            return null;
        D entity = (D) this.mapper.mapDataToObject(entityData);
        entity.setId(id);
        return entity;
    }

    /**
     * Crea un nuovo poi partendo da una serie di dati già validati
     * @param objectData
     * @return
     */
    public D create(Map<String, Object> objectData) throws Exception {
        try {
            D entity = (D) this.mapper.mapDataToObject(objectData);
              entity = (D) this.repository.create(entity, null);
            return entity;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Crea un nuovo poi partendo da una serie di dati già validati
     * @param objectData
     * @return
     */
    public D create(Map<String, Object> objectData, RegisteredUser author) throws Exception {
        if(author == null)
            throw new IllegalArgumentException("L'autore non può essere null");
        D entity = (D) this.mapper.mapDataToObject(objectData);
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
        D entity = (D) this.mapper.mapDataToObject(objectData);
        entity.setId(id);
        try {
            this.repository.update(entity, "");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return entity;
    }

    @Override
    public D delete(long id) throws Exception {
        D entity = this.getObjectById(id);
        if(entity == null)
            return null;
        int result = this.repository.delete(entity,null);
        if(result > 0)
            return entity;
        else
            return null;
    }
}
