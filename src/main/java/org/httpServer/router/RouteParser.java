package org.httpServer.router;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * Questa classe ha la responsabilità di interpretare e restituire le possibili informazioni
 * provenienti dalla richiesta http ricevuta su un endpoint/rotta.
 */
public class RouteParser {

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
     * @param requestPath
     * @return
     */
    public static int getQueryId(String requestPath){
        String[] segments = requestPath.split("/");
        if (segments.length > 3) {
            try {
                return Integer.parseInt(segments[3]);
            } catch (NumberFormatException e) {
               return 0;
            }
        }
        return 0;
    }


    /**
     * Estrae il parametro di ricerca da una url origine di richiesta http
     * @param requestPath
     * @return String la stringa di ricerca
     */
    public static String getQueryString(String requestPath){
        String[] segments = requestPath.split("\\?", 2);
        if (segments.length < 2) {
            return "";
        }
        String[] keyValue = segments[1].split("=",2);
        if(keyValue.length > 1 && "search".equals(keyValue[0])){
            return keyValue[1];
        }
        return "";
    }
}
