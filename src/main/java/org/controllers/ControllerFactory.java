package org.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.services.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public abstract class ControllerFactory implements HttpHandler {

    String requestPath;

    protected Service service;

    public  ControllerFactory(Service service) {
        this.service = service;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        this.requestPath = exchange.getRequestURI().getPath();
        switch (exchange.getRequestMethod()) {
            case "GET":    this.index();   break;
            case "POST":   this.create(exchange); break;
            case "PATCH":  this.update(); break;
            case "DELETE": this.delete(); break;
            default: this.index();
        }
    }
    protected abstract void index();
    protected abstract void create(HttpExchange exchange) throws IOException;
    protected abstract void update() throws IOException;
    protected abstract void delete() throws IOException;
    protected abstract void index(HttpExchange exchange) throws IOException;

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
     * Ritorna una risposta json positiva
     * @param exchange
     * @param response messaggio da includere nella risposta.
     * @throws IOException
     */
    protected void positiveResponse(HttpExchange exchange, String response) throws IOException {

        exchange.getResponseHeaders().set("Content-Type", "text/plain; charset=UTF-8");
        exchange.sendResponseHeaders(200, response.getBytes(StandardCharsets.UTF_8).length);

        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes(StandardCharsets.UTF_8));
        os.close();}

    /**
     * Ritorna una risposta json negativa
     * @param exchange
     * @param response messaggio da includere nella risposta.
     * @throws IOException
     */
    protected void errorResponse(HttpExchange exchange, String response, int errorCode) throws IOException {

        exchange.getResponseHeaders().set("Content-Type", "text/plain; charset=UTF-8");
        exchange.sendResponseHeaders(errorCode, response.getBytes(StandardCharsets.UTF_8).length);

        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes(StandardCharsets.UTF_8));
        os.close();}

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
