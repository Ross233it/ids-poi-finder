package org.controllers;

import com.sun.net.httpserver.HttpExchange;
import org.httpServer.AuthUtilities;
import org.httpServer.http.HttpRequest;
import org.httpServer.http.HttpResponses;
import org.models.users.RegisteredUser;
import org.services.IService;
import org.services.RegisteredUserService;

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

    @Override
    public void index() throws IOException {
        Map<String, String>queryParams = request.getQueryParams();
        if(!queryParams.isEmpty() && queryParams.get("search")!=null) {
            String queryStringSearchTerm = (String) queryParams.get("search");
            search(queryStringSearchTerm);
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
        RegisteredUser author = this.getAuthor();
        data.put("author", author);
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
        handleRequest(()->service.update(id, data), null);
    }

    /**
     * Gestisce una richiesta di ricerca attraverso un parametro inviato nella
     * richiesta http come query string.
     * @throws IOException
     */
    public void search(String queryStringSearchTerm) throws IOException {
        handleRequest(() -> { return service.search(queryStringSearchTerm);}, null);
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
            long id = this.request.getRequestId();
            handleRequest(() -> service.delete(id), "Record eliminato con successo");
        }
    }

    /**
     * Ritorna l'utente autenticato per la chiamata | null altrimenti.
     * @return
     */
    public static RegisteredUser getAuthor(){
        String accessToken = AuthUtilities.getAccessToken(exchange);
        RegisteredUserService service = new RegisteredUserService();
        return service.getByAccessToken(accessToken);
    }
}
