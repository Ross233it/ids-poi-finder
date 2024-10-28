package org.controllers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class PostDataHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if ("POST".equals(exchange.getRequestMethod())) {
            InputStream inputStream = exchange.getRequestBody();
            String requestBody = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);

            System.out.println("Dati ricevuti nel corpo della richiesta: " + requestBody);

            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> data = mapper.readValue(requestBody, Map.class);

            // Accedi ai singoli campi
            Map<String, Object> user = (Map<String, Object>) data.get("user");
            String name = (String) user.get("name");
            int age = (Integer) user.get("age");

            System.out.println("Nome: " + name);
            System.out.println("Età: " + age);

            // Crea la risposta
            String response = "Nome ricevuto: " + name + ", Età ricevuta: " + age;
            exchange.getResponseHeaders().set("Content-Type", "text/plain; charset=UTF-8");
            exchange.sendResponseHeaders(200, response.getBytes(StandardCharsets.UTF_8).length);

            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes(StandardCharsets.UTF_8));
            os.close();
        } else {
            exchange.sendResponseHeaders(405, -1);
        }
    }
}
