package org.httpServer.http;

import com.sun.net.httpserver.HttpExchange;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * Questa classe ha la responsabilità di interpretare e restituire le possibili informazioni
 * provenienti dalla richiesta http ricevuta su un endpoint/rotta. Rappresenta una richiesta http
 */
public class HttpRequest {

    private HttpExchange exchange;

    private Map<String, String> queryParams;

    private Map<String, String> pathParams;

    private String requestPath;

    private Map<String, Object> bodyStreamData;


    public HttpRequest(HttpExchange exchange) throws IOException {
        this.exchange = exchange;
        this.requestPath = exchange.getRequestURI().getPath();
        this.queryParams = new HashMap<String, String>();
        this.pathParams  = new HashMap<String, String>();
        this.bodyStreamData = this.bodyStreamDataInit();
        this.queryStringParamsInit();
    }

    /*** GETTERS ***/

    /**
     * Ritorna l'attuale oggetto exchange - rappresentativo della comunicazione in corso
     */
    public HttpExchange getExchange(){ return this.exchange; }

    /**
     * Ritorna l'attuale oggetto exchange - rappresentativo della comunicazione in corso
     */
    public String getRequestPath(){ return this.requestPath; }

    /**
     * Ritorna il body completo in formato stringa della request
     * @return l'id della request, zero altrimenti
     */
    public Map<String, Object> getBodyStreamData(){ return this.bodyStreamData; }


    /**
     * Ritorna il parametro id se presente nella url della richiesta
     * @return id long l'id della richiesta
     */
    public long getRequestId(){
        if(this.pathParams.get("id")!= null)
            return Long.parseLong(this.pathParams.get("id"));
        return 0;
    }

    /**
     * Ritorna i parametri presenti nella query string della richiesta
     * @return queryParams i parametri presenti nella query string
     */
    public Map<String, String>getQueryParams(){
        return this.queryParams;
    }

    /**
     * Ritorna i parametri dinamici presenti nella url della request
     * @return queryParams i parametri presenti nella query string
     */
    public Map<String, String>getPathParams(){
        return this.pathParams;
    }


    /*** SETTERS ***/

    /**
     * Recupera i parametri dinamici della rotta confrontandola con il pattern
     * ricevuto come parametro - recuperato dalla route
     * @param routePattern il pattern di costruzione dei parametri dinamici
     * @return params la mappa dei parametri con i rispettivi nomi e valori.
     */
    public void setPathParams(String routePattern) {
        String regex = routePattern.replaceAll("\\{(\\w+)\\}", "(\\\\w+)");
        String[] routeParts = routePattern.split("/");
        String[] pathParts = this.requestPath.split("/");

        if (routeParts.length == pathParts.length) {
            for (int i = 0; i < routeParts.length; i++) {
                if (routeParts[i].matches("\\{\\w+\\}")) {
                    String paramName = routeParts[i].replaceAll("[{}]", "");
                    this.pathParams.put(paramName, pathParts[i]);
                }
            }
        }
    }

    /*** SETTERS ***/

    /**
     * Recupera le informazioni dal body di una chiamata http
     * @return
     * @throws IOException
     */
    private Map<String, Object> bodyStreamDataInit() throws IOException {
        InputStream inputStream = this.exchange.getRequestBody();
        String requestBody = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);

        System.out.println("Dati ricevuti nel corpo della richiesta: " + requestBody);

        if (requestBody.isBlank()) {
            return new HashMap<String, Object>();
        }
        ObjectMapper objectMapper = new ObjectMapper();

        return objectMapper.readValue(requestBody, Map.class);
    }

    /**
     * Estrae i parametri di ricerca da una url origine di richiesta http
     * @return queryParams parametri della query string
     */
    private void queryStringParamsInit(){
        String query = this.exchange.getRequestURI().getQuery();
        if (query != null) {
            for (String param : query.split("&")) {
                String[] keyValue = param.split("=");
                if (keyValue.length == 2) {
                    this.queryParams.put(keyValue[0], keyValue[1]);
                }
            }
        }
    }
}
