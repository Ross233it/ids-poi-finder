package org.controllers;

import com.sun.net.httpserver.HttpExchange;
import org.httpServer.HttpResponses;
import org.httpServer.HttpUtilities;
import org.services.IService;

import java.io.IOException;
import java.util.Map;

public abstract class Controller<T> implements IController {

    protected String requestPath;

    protected IService service;

    protected HttpExchange exchange;

    public Controller(IService service) {
        this.service = service;
    }

    /**
     * Questo metodo ha la responsabilità di gestire le richieste http in arrivo
     * @param exchange
     * @throws IOException
     */
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        this.requestPath = exchange.getRequestURI().getPath();
        this.exchange = exchange;
        String method = exchange.getRequestMethod();
        this.handleRoutes(method);
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
     * Riceve dati tramite API ed avvia la procedura di creazione di oggetti.
     * Ritorna una response HTTP al chiamante.
     * @throws IOException
     */
    @Override
    public void create() throws IOException {
        try{
            Map<String, Object> data = HttpResponses.getStreamData(this.exchange);

            T newItem = (T) this.service.create(data);

            if(null != newItem) {
                HttpResponses.success(this.exchange, "Record creato con successo");
            }else
                HttpResponses.error(this.exchange, 500, "Si è verificato un problema nella creazione del record");
        }catch (Exception e) {
            HttpResponses.error(this.exchange, 500, e.getMessage());
        }
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
                HttpResponses.success(this.exchange, HttpResponses.objectToJson(item));
        } catch (Exception e) {
            HttpResponses.error(this.exchange, 500, e.getMessage());
        }
    }


    /**
     * Gestisce la richiesta di aggiornamento di una specifica risorsa.
     * @throws IOException
     */
    @Override
    public void update() throws IOException {    }

    /**
     * Gestisce la richiesta di eliminazione di una specifica risorsa.
     * @param id
     * @throws IOException
     */
    @Override
    public void delete(long id) throws IOException {
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


    /**
     * Gestisce i comportamenti attivati dalle chiamate su specifiche rotte http
     * @throws IOException
     */
    protected void handleRoutes(String method) throws IOException {
        int id = HttpUtilities.getQueryId(this.requestPath);
        switch (method.toUpperCase()) {
            case "GET":
                if( id > 0)
                    this.show(id);
                else
                    this.index();
                break;
            case "POST":
                this.create();
                break;
            case "PATCH":
                this.update();
                break;
            case "DELETE":
                this.delete(id);
                break;
            default:
                this.index();
        }
    }
}
