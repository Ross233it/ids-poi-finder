package org.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import org.services.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class ControllerFactory implements Controller{


    protected Service service;

    public  ControllerFactory(Service service) {
        this.service = service;
    }

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

    protected void positiveResponse(HttpExchange exchange, String response) throws IOException {

        exchange.getResponseHeaders().set("Content-Type", "text/plain; charset=UTF-8");
        exchange.sendResponseHeaders(200, response.getBytes(StandardCharsets.UTF_8).length);

        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes(StandardCharsets.UTF_8));
        os.close();}

    protected void errorResponse(HttpExchange exchange, String response, int errorCode) throws IOException {

        exchange.getResponseHeaders().set("Content-Type", "text/plain; charset=UTF-8");
        exchange.sendResponseHeaders(errorCode, response.getBytes(StandardCharsets.UTF_8).length);

        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes(StandardCharsets.UTF_8));
        os.close();}

}
