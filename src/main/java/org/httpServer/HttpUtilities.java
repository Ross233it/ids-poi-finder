package org.httpServer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class HttpUtilities {

    /**
     * Recupera le informazioni dal body di una chiamata http
     * @param exchange
     * @return
     * @throws IOException
     */
    public static Map<String, Object> getStreamData(HttpExchange exchange) throws IOException {
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
    public static Integer extractIdFromUrl(String url, String regex, int totalParts, int idPosition) {
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

    /**
     * Estrae l'id da una stringa in base ad una espressione regolare
     * @param requestPath
     * @return
     */
    public static int getQueryId(String requestPath) throws IllegalArgumentException {
        String[] segments = requestPath.split("/");
        if (segments.length > 3) {
            try {
                return Integer.parseInt(segments[3]);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Il valore dell'id non è un numero valido: " + segments[3], e);
            }
        }
        throw new IllegalArgumentException("Nessun id trovato nella URL: " + requestPath);
    }
}
