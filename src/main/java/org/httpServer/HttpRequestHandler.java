package org.httpServer;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.models.users.RegisteredUser;
import org.services.IService;
import org.services.RegisteredUserService;

import java.io.IOException;

/**
 * Questa classe astratta ha la responsabilità di gestire ed indirizzare le chiamatte http
 * in arrivo, richiamando i metodi corretti in base al tipo di richiesta.
 */
public abstract class HttpRequestHandler implements HttpHandler {
    protected String requestPath;

    protected IService service;

    protected HttpExchange exchange;

    protected RegisteredUser currentUser;
    /**
     * Questo metodo ha la responsabilità di gestire le richieste http in arrivo
     * @param exchange
     * @throws IOException
     */
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        this.requestPath = exchange.getRequestURI().getPath();
        this.exchange = exchange;
        String method = exchange.getRequestMethod();
        String accessToken = AuthUtilities.getAccessToken(exchange);
        if(accessToken != null && accessToken != ""){
            RegisteredUserService userService = new RegisteredUserService();
            this.currentUser = userService.getByAccessToken(accessToken);
        }
        this.handleRoutes(method);
    }

    /**
     * Gestisce i comportamenti attivati dalle chiamate su specifiche rotte http
     * @throws IOException
     */
    protected void handleRoutes(String method) throws IOException {
        switch (method.toUpperCase()) {
            case "GET":
                this.handleGetCalls();
                break;
            case "POST":
                this.handlePostCalls();
                break;
            case "PATCH":
                this.handlePatchCalls();
                break;
            case "DELETE":
                this.handleDeleteCalls();
                break;
            default:
                this.index();
        }
    }

    /**
     * Gestisce i differenti endpoint per le request http di tipo GET
     * @throws IOException
     */
    protected abstract void handleGetCalls() throws IOException;

    protected abstract void handlePostCalls() throws IOException;

    protected abstract void handlePatchCalls() throws IOException;

    protected abstract void handleDeleteCalls() throws IOException;

    protected abstract void index() throws IOException;

}
