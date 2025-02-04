package org.httpServer;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RequestTODELETE {

    private HttpExchange exchange;
    private Map<String, String> queryParams;
    private String body;

    public RequestTODELETE(HttpExchange exchange) throws IOException {
        this.exchange = exchange;
        this.queryParams = parseQueryParams();
        this.body = new String(exchange.getRequestBody().readAllBytes());
    }

    private Map<String, String> parseQueryParams() {
        Map<String, String> queryParams = new HashMap<>();
        String query = exchange.getRequestURI().getQuery();
        if (query != null) {
            for (String param : query.split("&")) {
                String[] keyValue = param.split("=");
                if (keyValue.length == 2) {
                    queryParams.put(keyValue[0], keyValue[1]);
                }
            }
        }
        return queryParams;
    }

    public String getBody() {
        return this.body;
    }

    public String getQueryParam(String key) {
        return queryParams.get(key);
    }
}

