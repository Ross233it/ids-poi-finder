package org.httpServer;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.controllers.Controller;
import org.models.users.RegisteredUser;
import org.services.RegisteredUserService;

import java.io.IOException;

/**
 * Questa classe astratta ha la responsabilità di gestire ed indirizzare le chiamatte http
 * in arrivo, richiamando i metodi corretti in base al tipo di richiesta.
 */
public class HttpRequestHandler<C extends Controller> implements HttpHandler {

    protected String requestPath;

    protected HttpExchange exchange;

    protected RegisteredUser currentUser;

    protected C controller;


    public HttpRequestHandler(C controller){
        this.controller = controller;
        this.controller.setHttpRequestHandler(this);
    }

    /** getters **/
    public String getRequestPath() {
        return this.requestPath;
    }

    public HttpExchange getExchange() {
        return this.exchange;
    }

    public RegisteredUser getCurrentUser() {
        return this.currentUser;
    }

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
                this.controller.index();
        }
    }

    /**
     * Gestisce i differenti endpoint per le request http di tipo GET
     * @throws IOException
     */
    protected void handleGetCalls() throws IOException {
        int id = HttpUtilities.getQueryId(this.requestPath);
        String queryStringSearchTerm = HttpUtilities.getQueryString(this.exchange.getRequestURI().toString());
        if( id > 0)
            this.controller.show(id);
        else if(queryStringSearchTerm != "")
            this.controller.search();
        else
            this.controller.index();
    }

    /**
     * Gestisce i differenti endpoint per le request http di tipo POST
     * @throws IOException
     */
    protected void handlePostCalls()throws IOException{
        this.controller.create();
    }

    /**
     * Gestisce i differenti endpoint per le request http di tipo PATCH
     * @throws IOException
     */
    protected void handlePatchCalls() throws IOException{
        this.controller.update();
    }

    /**
     * Gestisce i differenti endpoint per le request http di tipo DELETE
     * @throws IOException
     */
    protected void handleDeleteCalls() throws IOException{
        if(this.currentUser.hasRole("platformAdmin")) {
            int id = HttpUtilities.getQueryId(this.requestPath);
            if (id > 0)
                this.controller.delete(id);
        }else{
            HttpResponses.error(this.exchange, 500, "Non disponi dei permessi necessari per questa operazione");
        }
    }
}
