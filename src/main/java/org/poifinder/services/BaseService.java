package org.poifinder.services;

import jakarta.persistence.MappedSuperclass;
import org.poifinder.dataMappers.ContentReportMapper;
import org.poifinder.eventManager.EmailNotifier;
import org.poifinder.eventManager.EventManager;
import org.poifinder.eventManager.LogNotifier;
import org.poifinder.httpServer.auth.UserContext;
import org.poifinder.models.Content;

import org.poifinder.models.IModel;
import org.poifinder.models.poi.Poi;
import org.poifinder.models.users.RegisteredUser;
import org.poifinder.repositories.IRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@MappedSuperclass
public abstract class BaseService<D extends IModel> implements IService<D> {

    protected IRepository<D> repository;

    protected EventManager eventManager;

    @Autowired
    public BaseService(IRepository<D> repository) {
        this.repository = repository;
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

    /**
     * Fornisce il servizio di ricerca di un oggetto in base al suo
     * identificativo univoco
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public D getObjectById(Long id) throws Exception {
        Optional<D> result = repository.findById(id);
        return result.orElseThrow(() -> new Exception("Elemento non trovato"));
    }

    /**
     * Gestisce il servizio di creazione di una nuova entità a database
     * @param entity D  informazioni dell'oggetto da creare
     * @return object l'oggetto creato e salvato nello strato di persistenza.
     * @throws Exception
     */
    @Override
    public D create(D entity) throws Exception {
        RegisteredUser author = UserContext.getCurrentUser();
        return repository.save(entity);
    }

    /**
     * Gestisce il servizio di aggiornamento di una entità esistente a database
     * @param entity D  informazioni dell'oggetto da creare
     * @return object l'oggetto creato e salvato nello strato di persistenza.
     * @throws Exception
     */
    @Override
    public D update(Long id, D entity) throws Exception {
        entity.setId(id);
        return this.create(entity);
    }


    /**
     * Recupera un entità in base all'id e ne memorizza lo stato
     * @param id identificativo dell'entità
     * @param status lo stato da memorizzare per l'entità
     * @return entity l'entità con lo stato settato.
     * @throws Exception
     */
    @Override
    public D setStatus(Long id, String status) throws Exception {
        D entity = getObjectById(id);
        if(entity instanceof Content ){
            RegisteredUser approver = UserContext.getCurrentUser();
            ((Content) entity).setApprover(approver);
            ((Content) entity).setStatus(status);
        }
        return repository.save(entity);
    }

    /**
     * Recupera una entità in base all'id quindi la elimina
     * @param id l'identificativo dell'entità
     * @return l'entità eliminata
     * @throws Exception
     */
    @Override
    public D delete(Long id) throws Exception {
        D entity = repository.getById(id);
        repository.delete(entity);
        return entity;
    }


    /**
     * Servizio di segnalazione di un contenuto ad un utente
     * responsabile.
     * @param id l'id del contenuto segnalato
     * @param data informazione inviate con la segnalazione
     */
    public void reportContent(Long id, ContentReportMapper data) throws Exception {
        D entity =  repository.getById(id);
        Map<String, Object> reportData = new HashMap();
        String emailAddress = ((Content) entity).getApprover().getEmail();
            reportData.put("emailAddress", emailAddress);
            reportData.put("userData", data);
            reportData.put("reportedEntity", entity);
        eventManager.notify("content-report", reportData);
    }


    /**
     * Verifica se l'autore di un contenuto dispone dei privilegi necessari
     * per l'autopubblicazione di un poi.
     * @param content da valutare
     * @return il poi autopubblicato se l'utente dispone dei privilegi.
     */
    protected Content publishOrPending(Content content){
        RegisteredUser author = UserContext.getCurrentUser();
        List<String>roles = List.of("authContributor","curator", "animator", "platformAdmin");
        if(author != null && author.hasOneRole(roles)) {
            if (((Poi)content).getMunicipality().getId() == content.getAuthor().getMunicipality().getId()) {
                content.setStatus("published");
                content.setApprover(author);
            }
        }
        return content;
    }

    /**
     * Esegue la notifica di un evento di gestione contenuti.
     * @param content l'oggetto modificato
     */
    protected void notify(Content content){
        String eventType =  "new-pending-content";
        if(content.getStatus().equals("published"))
                eventType = "new-published-content";
        Map<String, Object>eventData = Map.of("NuovoPoi", content);
        eventManager.notify(eventType, eventData);
    }
}
