package org.services;

import org.dataMappers.DataMapper;
import org.eventManager.EmailNotifier;
import org.eventManager.EventManager;
import org.eventManager.LogNotifier;
import org.models.Content;

import org.models.users.RegisteredUser;
import org.repositories.Repository;

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

    /**
     * Gestisce il servizio di recupero di tutti gli oggetti di un certo
     * tipo.
     * @return List<D> una lista di tutti gli oggetti recuperati.
     */
    @Override
    public List<D> index() {
        try {
            List<Map<String, Object>>results = this.repository.index(null);
            List<D> objectList = this.mapper.mapDbDataToObjects(results);
            return objectList;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Crea un oggetto sulla base delle informazioni recuperate dallo strato di persistenza
     * @param queryParams parametri ricevuti tramite una query string
     * @return D l'oggetto creato | null altrimenti.
     * @throws Exception
     */
    @Override
    public List<D> filter(Map<String, String> queryParams) throws Exception {
        List<Map<String, Object>> entityData = this.repository.search(queryParams, null);
        if(entityData == null)
            return null;
        List<D> entities = (List<D>) mapper.mapDbDataToObjects(entityData);
        return entities;
    }

    /**
     * Gestisce il servizio di ricerca di un oggetto in base al
     * suo identificativo unico
     *
     * @param id l'identificativo unico dell'oggetto da ricercare
     * @return entity D l'oggetto trovato | null altrimenti
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
     * Gestisce il servizio di creazione di un nuovo poi
     * partendo da una serie di dati
     * @param objectData i dati relativi al nuovo oggetto
     * @return entity D l'oggetto creato
     */
    @Override
    public D create(Map<String, Object> objectData) throws Exception {
        if((RegisteredUser) objectData.get("author") == null)
            throw new IllegalArgumentException("L'autore non può essere null");
        D entity = (D) this.mapper.mapDataToObject(objectData);
        entity.setAuthor((RegisteredUser) objectData.get("author"));
        try {
            this.repository.create(entity, "");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return entity;
    }

    /**
     * Gestisce il servizio di aggiornamento di un oggetto partendo da una
     * serie di dati
     * @param objectData le informazioni circa l'oggetto e le modifiche
     * @return entity D l'oggetto modificato
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

    /**
     * Gestisce il servizio di rimozione di oggetti dal sistema.
     *
     * @param id
     * @return
     * @throws Exception
     */
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

    /**
     * Gestisce il servizio di aggiornamento dello stato di un
     * contenuto
     * @param data i dati del contenuto di cui modificare lo stato
     * @return l'oggetto aggiornato con il nuovo stato
     * @throws Exception
     */
    @Override
    public D setStatus(Map<String, Object> data) throws Exception {

        String status = (String) data.get("status");

        D entity = this.getObjectById( (Long) data.get("id"));
        entity.setStatus( (String) data.get("status") );
        entity.setApprover( (RegisteredUser) data.get("author"));
        if(entity != null){
            entity.setStatus((String) data.get("status"));
            repository.setStatus(entity);
            eventManager.notify("Nuovo Punto di interesse pubblicato");
            return entity;
        }
        return null;
    }


    public void reportContent(){}
}
