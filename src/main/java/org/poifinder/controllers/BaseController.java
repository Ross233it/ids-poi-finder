package org.poifinder.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import org.poifinder.dataMappers.ContentReportMapper;
import org.poifinder.dataMappers.DataMapper;
import org.poifinder.dataMappers.Views;
import org.poifinder.httpServer.auth.AuthMiddleware;
import org.poifinder.models.IModel;
import org.poifinder.services.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/{resource}")
public abstract class BaseController<T extends IModel>  implements IController<T> {

    protected BaseService<T> service;

    @Autowired
    protected AuthMiddleware authMiddleware;

    public BaseController(BaseService<T> service) {
        this.service = service;
    }

    /**
     * Gestisce la richiesta di visualizzazione di numerosi oggetti in
     * base ad eventuali filtri della query string ed all'utente richiedente
     * @throws IOException
     */
    @Override
    @GetMapping
    @JsonView(Views.Public.class)
    public ResponseEntity<List<T>> index(@RequestParam String queryString) throws IOException {
        try {
            List<T> result = service.index();
            if(result == null)
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * Gestisce la richiesta http di creazione di una nuova risorsa
     * Ritorna una response HTTP al chiamante.
     * @throws IOException
     */
    @Override
//    @PostMapping
    public ResponseEntity<T> create(@RequestBody DataMapper<T> data) throws IOException {
       return null;
//        Map<String, Object> data = request.getBodyStreamData();
//        data.put("author", UserContext.getCurrentUser());
//        handleRequest(()->service.create(data), null);
    }

    /**
     * Gestisce la richiesta di aggiornamento di una specifica risorsa.
     * @throws IOException
     */
    @Override
    @PatchMapping("/{id}")
    @JsonView(Views.Public.class)
    public ResponseEntity<T> update(@PathVariable Long id,
                                    DataMapper<T> entityData) throws IOException {
//        try {
//            return ResponseEntity.ok(service.update(id, entity));
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
        return null;
    }

    /**
     * Gestisce la richiesta di visualizzazione di una specifica risorsa.
     * @throws IOException
     */
    @Override
    @GetMapping("/{id}")
    public  ResponseEntity<T> show(@PathVariable Long id) throws IOException {
        try {
            T result = service.getObjectById(id);
            if(result == null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * Gestisce la richiesta di eliminazione di una specifica risorsa.
     * @throws IOException
     */
    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) throws IOException {
        try {
            service.delete(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.noContent().build();
//        if(this.request.getRequestId() > 0) {
//            handleRequest(() -> service.delete(
//                    request.getRequestId()
//            ), "Record eliminato con successo");
//        }
    }

    /**
     * Gestisce la richiesta di validazione di un contenuto
     * @throws Exception
     */
    public void setStatus() throws Exception{
//        Map<String, Object> data = request.getBodyStreamData();
//        if(UserContext.getCurrentUser() != null)
//            data.put("approver", UserContext.getCurrentUser());
//        long id = request.getRequestId();
//        if(data.get("status") == null || id == 0)
//            CustomResponse.error(this.exchange, 404, "Dati mancanti");
//        data.put("id", id);
//        handleRequest(()-> service.setStatus(data), null);
    }

    /**
     * Gestisce le richieste di segnalazione dei Poi ad
     * un utente responsabile.
     */
    @PostMapping("/{id}/report-content")
    public void reportContent(@PathVariable Long id, @RequestBody ContentReportMapper data) throws Exception {
        service.reportContent(id, data);
        return "success";
    }
}
