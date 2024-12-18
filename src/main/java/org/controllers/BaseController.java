package org.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.httpServer.HttpResponses;
import org.services.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public abstract class BaseController<T> implements HttpHandler {

    String requestPath;

    protected Service service;

    protected HttpResponses responses;

    protected HttpExchange exchange;

    public BaseController(Service service) {
        this.service = service;
        this.responses = new HttpResponses();
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        this.requestPath = exchange.getRequestURI().getPath();
        this.exchange = exchange;
        String method = exchange.getRequestMethod();
        switch (exchange.getRequestMethod()) {
            case "GET":    this.index();   break;
            case "POST":   this.create(); break;
            case "PATCH":  this.update(); break;
            case "DELETE": this.delete(); break;
            default: this.index();
        }
    }

    /**
     * Riceve dati tramite API ed avvia la procedura di creazione di oggetti.
     * Ritorna una response HTTP al chiamante.
     * @throws IOException
     */
    protected void create() throws IOException {
        try{
            Map<String, Object> data = this.getStreamData(this.exchange);

            T newMunicipality = (T) this.service.create(data);

            if(null != newMunicipality) {
                this.responses.success(this.exchange, "Record creato con successo");
            }else
                this.responses.error(this.exchange, 500, "Si è verificato un problema nella creazione del record");
        }catch (Exception e) {
            this.responses.error(this.exchange, 500, e.getMessage());
        }
    }

    protected abstract void index()  throws IOException;
    protected abstract void update() throws IOException;
    protected abstract void delete() throws IOException;
//    protected abstract void index(HttpExchange exchange) throws IOException;

    /**
     * Get http request json data
     * @param exchange
     * @return
     * @throws IOException
     */
    protected Map<String, Object> getStreamData(HttpExchange exchange) throws IOException {
        InputStream inputStream = exchange.getRequestBody();
        String requestBody = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);

        System.out.println("Dati ricevuti nel corpo della richiesta: " + requestBody);

        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> data = mapper.readValue(requestBody, Map.class);
        return data;
    }



    /**
     * Estrae l'id da una stringa in base ad una espressione regolare
     * @param url
     * @param regex
     * @param totalParts
     * @param idPosition
     * @return Integer l'id dell'oggetto da ricercare
     */
    protected Integer extractIdFromUrl(String url, String regex, int totalParts, int idPosition) {
        String[] parts = url.split("/");
        if (parts.length == totalParts) {
            try {
                return Integer.valueOf(parts[idPosition]);
            } catch (NumberFormatException e) {
                return null;
            }
        }
        return null;
    }

}
