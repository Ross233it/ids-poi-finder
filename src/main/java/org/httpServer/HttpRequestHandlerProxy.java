package org.httpServer;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;


import java.io.IOException;

/**
 * Questa classe funge da proxy per il metodo di gestione delle richieste http
 * La sua responsabilità è quella di inoltrare le chiamate ai giusti metodi dei controllers
 *
 */
public class HttpRequestHandlerProxy implements HttpHandler {

    HttpRequestHandler realHandler;

    public HttpRequestHandlerProxy(HttpHandler realHandler){
        this.realHandler = (HttpRequestHandler) realHandler;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {

    }
}
