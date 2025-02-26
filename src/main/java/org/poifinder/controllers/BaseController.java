package org.poifinder.controllers;

import com.fasterxml.jackson.annotation.JsonView;

import org.poifinder.dataMappers.ContentReportMapper;
import org.poifinder.dataMappers.Views;

import org.poifinder.models.IModel;

import org.poifinder.services.BaseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
public class BaseController<T extends IModel>  implements IController<T> {

    protected BaseService<T> service;

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
    public ResponseEntity index() throws IOException {
        try {
            List<T> result = service.index();
            if(result == null)
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Impossibile recuperare le entità. "+ e);
        }
    }

    /**
     * Gestisce la richiesta http di creazione di una nuova risorsa
     * Ritorna una response HTTP al chiamante.
     * @throws IOException
     */
    @Override
    @PostMapping
    @JsonView(Views.Public.class)
    public ResponseEntity create(@RequestBody T entity) throws IOException {
        try {
            T createdEntity = service.create(entity);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdEntity);
        } catch (Exception e) {
            throw new RuntimeException("Errore durante la creazione dell'entità", e);
        }
    }

    /**
     * Gestisce la richiesta di aggiornamento di una specifica risorsa.
     * @throws IOException
     */
    @Override
    @PatchMapping("/{id}")
    @JsonView(Views.Public.class)
    public ResponseEntity update(@PathVariable Long id, @RequestBody T entity) throws Exception {
        try {
            T updatedEntity = service.update(id, entity);
            return ResponseEntity.ok(updatedEntity);
        } catch (Exception e) {
            throw new RuntimeException("Errore durante l'aggiornamento dell'entità", e);
        }
    }

    /**
     * Gestisce la richiesta di visualizzazione di una specifica risorsa.
     * @throws IOException
     */
    @Override
    @GetMapping("/{id}")
    @JsonView(Views.Public.class)
    public  ResponseEntity show(@PathVariable Long id) throws IOException {
        try {
            T result = service.getObjectById(id);
            if(result == null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Impossibile trovare la risorsa richiesta");
            }
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            throw new RuntimeException("Errore durante la validazione dell'entità", e);
        }
    }

    /**
     * Gestisce la richiesta di eliminazione di una specifica risorsa.
     * @throws IOException
     */
    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) throws RuntimeException{
        try {
            service.delete(id);
            return  ResponseEntity.ok("Risorsa eliminata con successo");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Si è verificato un errore durante la cancellazione: " + e.getMessage());
        }
    }

    /**
     * Gestisce la richiesta di validazione di un contenuto
     * @param id l'id dell'oggetto da validare
     * @return ResponseEntity con l'entità validata.
     * @throws Exception
     */
    @PatchMapping("/{id}/validate")
    @JsonView(Views.Public.class)
    public ResponseEntity validate(@PathVariable Long id) throws Exception{
        try {
            T  entity = (T) service.setStatus(id, "published");
            return ResponseEntity.ok(entity);
        } catch (Exception e) {
            throw new RuntimeException("Errore durante la validazione dell'entità", e);
        }
    }


    /**
     * Gestisce la richiesta di validazione di un contenuto
     * @param id l'id dell'oggetto da validare
     * @return ResponseEntity con l'entità validata.
     * @throws Exception
     */
    @PatchMapping("/{id}/reject")
    @JsonView(Views.Public.class)
    public ResponseEntity reject(@PathVariable Long id) throws Exception{
        try {
            T  entity = (T) service.setStatus(id, "rejected");
            return ResponseEntity.ok(entity);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Si è verificato un errore - impossibile aggiornare lo stato dell'entità "+ e);
        }
    }

    /**
     * Gestisce le richieste di segnalazione dei Poi ad
     * un utente responsabile.
     */
    @PostMapping("/{id}/report-content")
    public ResponseEntity reportContent(@PathVariable Long id, @RequestBody ContentReportMapper data) throws Exception {
        try {
            service.reportContent(id, data);
            return ResponseEntity.ok("Segnalazione inviata con successo, grazie per il tuo contributo.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Non è stato possibile trasmettere la segnalazione");
        }
    }
}
