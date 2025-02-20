package org.poifinder.services;

import org.poifinder.dataMappers.DataMapper;
import org.poifinder.eventManager.EmailNotifier;
import org.poifinder.eventManager.EventManager;
import org.poifinder.eventManager.LogNotifier;
import org.poifinder.models.Content;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public  class BaseService<D extends Content> implements IService<D> {

    protected JpaRepository<D, Long> repository;

    protected EventManager eventManager;

    protected DataMapper mapper;

    @Autowired
    public BaseService(JpaRepository<D, Long> repository,
                       DataMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
        this.initListeners();
    }

    /**
     * Inizializza i listener associati agli eventi
     * generati dal servizio
     */
    private void initListeners(){
        this.eventManager = new EventManager();
        eventManager.subscribe(new EmailNotifier());
        eventManager.subscribe(new LogNotifier());
    }


    @Override
    public List<D> index() {
        return repository.findAll();
    }

    @Override
    public List<D> filter(Map<String, String> queryParams) throws Exception {
        return List.of();
    }

    @Override
    public D setStatus(Map<String, Object> data) throws Exception {
        return null;
    }


    @Override
    public D getObjectById(long id) throws Exception {
        Optional<D> result = repository.findById(id);
        return result.orElseThrow(() -> new Exception("Elemento non trovato"));
    }


    @Override
    @Transactional
    public D create(Map<String, Object> data) throws Exception {
        D entity = (D) mapper.mapDataToObject(data);
        return repository.save(entity);
    }

    @Override
    @Transactional
    public D update(long id, Map<String, Object> data) throws Exception {
        D entity = getObjectById(id);
        mapper.updateEntityFromMap(entity, data);
        return repository.save(entity);
    }

    @Override
    public D delete(long id) throws Exception {
        return null;
    }


    public void deleteById(Long id) {
         repository.deleteById(id);
    }


    public List<D> search(Map<String, String> params) throws Exception {
//        return repository.search(params);
        return null;
    }


    /**
     * Servizio di segnalazione di un contenuto ad un utente
     * responsabile.
     */
    public void reportContent() throws Exception {
//        long id = request.getRequestId();
//        Map<String, Object> reportData = request.getBodyStreamData();
//        Map<String, Object> entityData =  this.repository.getById(id, null);
//        D entity = (D) this.mapper.mapDataToObject(entityData);
//        reportData.put("Poi id", id);
//        this.eventManager.notify("content report", reportData);
    }
}
