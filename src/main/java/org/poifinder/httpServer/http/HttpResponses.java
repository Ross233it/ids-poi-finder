package org.poifinder.httpServer.http;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Questa classe ha la responsabilità di implementare le risposte http
 * e di eseguirle
 */
public class HttpResponses{

    /**
     * Genera una risposta di successo e la invia
     * @param exchange
     * @param message messaggio testuale di conferma.
     * @throws IOException
     */
    public static void success(HttpExchange exchange, String message) throws IOException {
        sendResponse(exchange, 200, message);
    }

    /**
     * Genera una risposta di errore e la invia
     * @param exchange
     * @param errorMessage messaggio testuale di errore.
     * @throws IOException
     */
   public static void error(HttpExchange exchange, int statusCode, String errorMessage) throws IOException {
        sendResponse(exchange, statusCode, errorMessage);
    }

    /**
     * Invia una risposta in risposta ad una chiamata http
     * @param exchange
     * @param statusCode lo stato http della chiamata
     * @param message messaggio testuale di risposta.
     * @throws IOException
     */
    private static void sendResponse(HttpExchange exchange, int statusCode, String message) throws IOException {
        exchange.getResponseHeaders().add("Content-Type", "application/json; charset=UTF-8");
        exchange.sendResponseHeaders(statusCode, message.getBytes().length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(message.getBytes());
        }
    }
}
