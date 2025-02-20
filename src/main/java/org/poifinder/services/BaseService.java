package org.poifinder.services;

import org.poifinder.dataMappers.DataMapper;
import org.poifinder.eventManager.EmailNotifier;
import org.poifinder.eventManager.EventManager;
import org.poifinder.eventManager.LogNotifier;
import org.poifinder.httpServer.http.HttpRequest;
import org.poifinder.models.Content;
import org.poifinder.repositories.IRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public abstract class BaseService<D extends Content> implements IService<D> {

    protected IRepository<D> repository;

    protected EventManager eventManager;

//    protected DataMapper mapper;


    public BaseService(IRepository<D> repository,
                       EventManager eventManager,
                       DataMapper mapper
                       EmailNotifier emailNotifier,
                       LogNotifier logNotifier
    ) {
        this.repository = repository;
        this.eventManager = eventManager;
        this.mapper = mapper;
        eventManager.subscribe(emailNotifier);
        eventManager.subscribe(logNotifier);
    }

    @Override
    public List<D> index() {
        return repository.findAll();
    }


    @Override
    public D getObjectById(long id) throws Exception {
        Optional<D> result = repository.findById(id);
        return result.orElseThrow(() -> new Exception("Elemento non trovato"));
    }


    @Override
    @Transactional
    public D create(Map<String, Object> data) throws Exception {
        D entity = mapper.mapToEntity(data);
        return repository.save(entity);
    }

    @Override
    @Transactional
    public D update(long id, Map<String, Object> data) throws Exception {
        D entity = getObjectById(id);
        updateEntityFromMap(entity, data);
        return repository.save(entity);
    }


    public void deleteById(Long id) {
        return repository.deleteById(id);
    }


    public List<D> search(String searchField) {
        return repository.search(searchField);
    }


    @Override
    @Transactional
    public D delete(long id) throws Exception {
        D entity = getObjectById(id);
        repository.delete(entity);
        return entity;
    }

//    protected abstract D mapToEntity(Map<String, Object> data) throws Exception;

//    protected abstract void updateEntityFromMap(D entity, Map<String, Object> data) throws Exception;
    /**
     * Servizio di segnalazione di un contenuto ad un utente
     * responsabile.
     * @param request
     */
    public void reportContent(HttpRequest request) throws Exception {
        long id = request.getRequestId();
        Map<String, Object> reportData = request.getBodyStreamData();
        Map<String, Object> entityData =  this.baseRepository.getById(id, null);
        D entity = (D) this.mapper.mapDataToObject(entityData);
        reportData.put("Poi id", id);
        this.eventManager.notify("content report", reportData);
    }


}
