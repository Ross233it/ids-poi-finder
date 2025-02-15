package org.httpServer.http;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.controllers.Controller;
import org.httpServer.auth.AuthMiddleware;
import org.httpServer.auth.UserContext;
import org.httpServer.router.Router;
import org.models.users.RegisteredUser;

import java.io.IOException;

/**
 * Questa classe instrada le richieste http in entrata ad
 * una classe router.
 */
public class HttpRequestHandler<C extends Controller> implements HttpHandler {

    /**
     * Questo metodo ha la responsabilità di gestire le richieste http in arrivo
     * @param exchange
     * @throws IOException
     */
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String requestPath = exchange.getRequestURI().getPath();
        Router router = new Router(exchange);
//        AuthMiddleware middleware = new AuthMiddleware();
//        RegisteredUser currentUser = middleware.getCurrentUser(exchange);
//        UserContext.setCurrentUser(currentUser);
        router.dispatch(requestPath);
//        UserContext.clear();
    }
}
