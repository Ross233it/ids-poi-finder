package org.controllers;

import com.sun.net.httpserver.HttpExchange;
import org.httpServer.auth.UserContext;
import org.httpServer.http.HttpRequest;
import org.httpServer.http.HttpResponses;
import org.services.IService;

import java.io.IOException;
import java.util.Map;

public abstract class Controller<T, S extends IService>  implements IController<T> {

    protected S service;

    protected static HttpExchange exchange;

    protected HttpRequest request;

    public Controller(S service, HttpRequest request) {
        this.service = service;
        this.request  = request;
        this.exchange = request.getExchange();
    }

    /**
     * Gestisce e centralizza l'esecuzione dei singoli metodi del controller.
     * @param action la funzione da esseguire
     * @param successMessage il messaggio da ritornare in caso di successo.
     * @throws IOException
     */
    protected void handleRequest(ControllerRequestHandler<Object> action, String successMessage) throws IOException {
        try {
            Object result = action.handle();
            if (result == null) {
                HttpResponses.error(exchange, 404, "Record non trovato");
            } else {
                HttpResponses.success(exchange, successMessage != null ? successMessage : result.toString());
            }
        } catch (Exception e) {
            HttpResponses.error(exchange, 500, e.getMessage());
        }
    }

    /**
     * Gestisce la richiesta di visualizzazione di numerosi oggetti in
     * base ad eventuali filtri della query string ed all'utente richiedente
     * @throws IOException
     */
    @Override
    public void index() throws IOException {
        Map<String, String>queryParams = request.getQueryParams();
        if(!queryParams.isEmpty()) {
            handleRequest(()-> service.filter(queryParams), null);
        }
        handleRequest(()-> service.index(), null);
    }

    /**
     * Gestisce la richiesta http di creazione di una nuova risorsa
     * Ritorna una response HTTP al chiamante.
     * @throws IOException
     */
    @Override
    public void create() throws IOException {
        Map<String, Object> data = request.getBodyStreamData();
        data.put("author", UserContext.getCurrentUser());
        handleRequest(()->service.create(data), null);
    }

    /**
     * Gestisce la richiesta di aggiornamento di una specifica risorsa.
     * @throws IOException
     */
    @Override
    public void update() throws IOException {
        long id = request.getRequestId();
        Map<String, Object> data = this.request.getBodyStreamData();
        data.put("author", UserContext.getCurrentUser());
        handleRequest(()->service.update(id, data), null);
    }

    /**
     * Gestisce la richiesta di visualizzazione di una specifica risorsa.
     * @throws IOException
     */
    @Override
    public void show() throws IOException {
        if(this.request.getRequestId() > 0){
            handleRequest(()-> {return service.getObjectById(request.getRequestId());}, null);
        }
    }

    /**
     * Gestisce la richiesta di eliminazione di una specifica risorsa.
     * @throws IOException
     */
    @Override
    public void delete() throws IOException {
        if(this.request.getRequestId() > 0) {
            handleRequest(() -> service.delete(
                    request.getRequestId()
            ), "Record eliminato con successo");
        }
        return;
    }

    /**
     * Gestisce la richiesta di validazione di un contenuto
     * @throws Exception
     */
    public void setStatus() throws Exception{
        Map<String, Object> data = request.getBodyStreamData();
        data.put("approver", UserContext.getCurrentUser());
        long id = request.getRequestId();
        if(data.get("status") == null || id == 0)
            HttpResponses.error(this.exchange, 404, "Dati mancanti");
        data.put("id", id);
        handleRequest(()-> service.setStatus(data), null);
    }

    public void reportContent(){
//        handleRequest(()-> service.reportContent(), null);
    }

}
