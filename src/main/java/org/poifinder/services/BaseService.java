package org.poifinder.services;

import jakarta.persistence.MappedSuperclass;
import org.poifinder.dataMappers.ContentReportMapper;
import org.poifinder.dataMappers.DataMapper;
import org.poifinder.eventManager.EmailNotifier;
import org.poifinder.eventManager.EventManager;
import org.poifinder.eventManager.LogNotifier;
import org.poifinder.models.Content;

import org.poifinder.models.IModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@MappedSuperclass
public class BaseService<D extends IModel> implements IService<D> {

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


    /**
     * Fornisce il servizio di ricerca di tutte le entità di un certo
     * tipo presenti a sistema.
     * @return
     */
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

    /**
     * Fornisce il servizio di ricerca di un oggetto in base al suo
     * identificativo univoco
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public D getObjectById(long id) throws Exception {
        Optional<D> result = repository.findById(id);
        return result.orElseThrow(() -> new Exception("Elemento non trovato"));
    }


    @Override
    public DataMapper<D> create(DataMapper<D> entity) throws Exception {
//        D entity = (D) mapper.mapDataToObject(data);
//        return repository.save(entity);
        return null;
    }

    @Override
    public DataMapper<D> update(long id, DataMapper<D> entity) throws Exception {
//        Optional<D> existingEntity = repository.findById(id);
//        if (existingEntity.isEmpty()) {
//            throw new EntityNotFoundException("Entità con ID " + id + " non trovata.");
//        }
//        D updatedEntity = existingEntity.get();
//
//        BeanUtils.copyProperties(entity, updatedEntity, "id");
//
//        return repository.save(updatedEntity);

        return null;
    }



    @Override
    public D delete(long id) throws Exception {
        return null;
    }

//        D entity = getObjectById(id);
//        mapper.updateEntityFromMap(entity, data);
//        return repository.save(entity);
//    }



    public List<D> search(Map<String, String> params) throws Exception {
//        return repository.search(params);
        return null;
    }


    /**
     * Servizio di segnalazione di un contenuto ad un utente
     * responsabile.
     */
    public void reportContent(Long id, ContentReportMapper data) throws Exception {
        D entity = D this.repository.getReferenceById(id);
        this.eventManager.notify("Content Report", data);
    }
}
