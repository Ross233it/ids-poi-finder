package org.controllers;

import com.sun.net.httpserver.HttpExchange;
import org.httpServer.HttpResponses;
import org.httpServer.HttpUtilities;
import org.services.IService;

import java.io.IOException;
import java.util.Map;

public abstract class Controller<T, S extends IService>  implements IController<T> {

    protected S service;

    protected HttpExchange exchange;

    protected String requestPath;

    public Controller(S service, HttpExchange exchange) {
        this.service = service;
        this.exchange = exchange;
        this.requestPath = exchange.getRequestURI().getPath();
    }

    @Override
    public void index() throws IOException {
        try{
            String parsedData = this.service.index();
            if(parsedData == null)
                HttpResponses.error(this.exchange, 404, "Nessun record trovato");
            else
                HttpResponses.success(this.exchange, parsedData);
        }catch (Exception e) {
            HttpResponses.error(this.exchange, 500, e.getMessage());
        }
    }

    /**
     * Gestisce la richiesta http di creazione di una nuova risorsa
     * Ritorna una response HTTP al chiamante.
     * @throws IOException
     */
    @Override
    public void create() throws IOException {
        try{
            Map<String, Object> data = HttpResponses.getStreamData(this.exchange);

            T newItem = (T) this.service.create(data);

            if(newItem != null) {
                HttpResponses.success(this.exchange, "Record creato con successo");
            }else
                HttpResponses.error(this.exchange, 500, "Si è verificato un problema nella creazione del record");
        }catch (Exception e) {
            HttpResponses.error(this.exchange, 500, e.getMessage());
        }
    }


    /**
     * Gestisce la richiesta di aggiornamento di una specifica risorsa.
     * @throws IOException
     */
    @Override
    public void update() throws IOException {
        int id = HttpUtilities.getQueryId(this.requestPath);
        System.out.println("ID: " + id);
        if (id <= 0) {
            HttpResponses.error(exchange, 404, "Record non trovato");
            return;
        }
        try{
            Map<String, Object> data = HttpResponses.getStreamData(this.exchange);

            T newItem = (T) this.service.update(id, data);

            if(newItem != null) {
                HttpResponses.success(exchange, "Record creato con successo");
            }else
                HttpResponses.error(exchange, 500, "Si è verificato un problema nella creazione del record");
        }catch (Exception e) {
            HttpResponses.error(exchange, 500, e.getMessage());
        }
    }

    /**
     * Gestisce una richiesta di ricerca attraverso un parametro inviato nella
     * richiesta http come query string.
     * @throws IOException
     */
    public void search() throws IOException {
        String fullPath = this.exchange.getRequestURI().toString();
        String queryStringSearchTerm = HttpUtilities.getQueryString(fullPath);
        if(queryStringSearchTerm != ""){
            try {
                T item = (T) service.search(queryStringSearchTerm);
                if(item == null)
                    HttpResponses.error(this.exchange, 404, "Record non trovato");
                else
                    HttpResponses.success(this.exchange, HttpResponses.objectToJson(item));
            } catch (Exception e) {
                HttpResponses.error(this.exchange, 500, e.getMessage());
            }
        }else
            HttpResponses.error(this.exchange, 404, "Impossibile trovare risultati");
    }

    /**
     * Gestisce la richiesta di visualizzazione di una specifica risorsa.
     * @param id identificativo univoco della risorsa.
     * @throws IOException
     */
    @Override
    public void show(long id) throws IOException {
        try {
            T item = (T) this.service.getObjectById(id);
            if(item == null)
                HttpResponses.error(this.exchange, 404, "Record non trovato");
            else
                HttpResponses.success(this.exchange, item.toString());
        } catch (Exception e) {
            HttpResponses.error(this.exchange, 500, e.getMessage());
        }
    }

    /**
     * Gestisce la richiesta di eliminazione di una specifica risorsa.
     * @param id
     * @throws IOException
     */
    @Override
    public void delete(long id) throws IOException {
        HttpExchange exchange = this.exchange;
        try {
            T deleted = (T) this.service.delete(id);
            if(deleted != null)
                HttpResponses.success(this.exchange, "Record eliminato con successo");
            else
                HttpResponses.error(this.exchange, 404, "Record non trovato");
        } catch (Exception e) {
            HttpResponses.error(this.exchange, 500, e.getMessage());
        }
    }
}
