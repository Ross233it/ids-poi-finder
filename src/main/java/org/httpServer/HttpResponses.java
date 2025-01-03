package org.httpServer;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;

public class HttpResponses  extends HttpUtilities{

    /**
     * Genera una risposta di successo e la invia
     * @param exchange
     * @param message
     * @throws IOException
     */
    public static void success(HttpExchange exchange, String message) throws IOException {
        sendResponse(exchange, 200, message);
    }

    /**
     * Genera una risposta di errore e la invia
     *
     * @param exchange
     * @param errorMessage
     * @throws IOException
     */
   public static void error(HttpExchange exchange, int statusCode, String errorMessage) throws IOException {
        sendResponse(exchange, statusCode, errorMessage);
    }

    /**
     * Invia una risposta in risposta ad una chiamata http
     * @param exchange
     * @param statusCode
     * @param message
     * @throws IOException
     */
    private static void sendResponse(HttpExchange exchange, int statusCode, String message) throws IOException {
        exchange.getResponseHeaders().add("Content-Type", "application/json; charset=UTF-8");
        exchange.sendResponseHeaders(statusCode, message.getBytes().length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(message.getBytes());
        }
    }

    /**
     * Recupera i parametri di un oggetto qualsiasi e li trasforma in una stringa json
     * @param obj
     * @return
     * @throws IllegalAccessException
     */
    public static String objectToJson(Object obj) throws IllegalAccessException {
        if (obj == null) {
            return "{}";
        }
        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("{");

        Field[] fields = obj.getClass().getDeclaredFields();

        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            field.setAccessible(true);

            String fieldName = field.getName();
            Object fieldValue = field.get(obj);

            jsonBuilder.append("\"").append(fieldName).append("\": ");

            if (fieldValue instanceof String) {
                jsonBuilder.append("\"").append(fieldValue).append("\"");
            } else {
                jsonBuilder.append(fieldValue);
            }

            if (i < fields.length - 1) {
                jsonBuilder.append(", ");
            }
        }
        jsonBuilder.append("}");
        return jsonBuilder.toString();
    }
}
