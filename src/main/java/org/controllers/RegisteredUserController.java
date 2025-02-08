package org.controllers;

import org.dataMappers.DataMapper;
import org.httpServer.http.HttpRequest;
import org.httpServer.http.HttpResponses;
import org.models.users.RegisteredUser;

import org.services.RegisteredUserService;

import java.io.IOException;
import java.util.Map;

import static org.httpServer.AuthUtilities.getAccessToken;

/**
 * Questa classe ha la responsabilità di gestire le chiamate e le risposte
 * da e verso una interfaccia utente tramite Api e protocollo http.
 */
public class RegisteredUserController extends Controller<RegisteredUser, RegisteredUserService> {

    public RegisteredUserController(RegisteredUserService userService, HttpRequest request) {
        super(userService, request);
    }

    /**
     * Gestisce la richiesta di login
     * @throws IOException
     */
    public void login() throws Exception {
        try {
            Map<String, Object> data = request.getBodyStreamData();
            String accessToken =  this.service.login(data);
            if(accessToken == null || accessToken == "")
                HttpResponses.error(exchange, 404, "Autenticazione fallita");
            else
                HttpResponses.success(exchange, "{ accessToken: " + accessToken + " }" );
        } catch (Exception e) {
            HttpResponses.error(exchange, 500, e.getMessage());
        }
    }

    /**
     * Gestisce la richiesta di logout
     * @throws Exception
     */
    public void logout() throws Exception{
        try {
            String accessToken = getAccessToken(exchange);
            RegisteredUser currentUser = this.service.getByAccessToken(accessToken);
            if(currentUser != null){
                this.service.logout(currentUser);
                HttpResponses.success(exchange, "Logout effettuato");
            }else
                HttpResponses.error(exchange, 404, "Nessun utente loggato");
        } catch (Exception e) {
            HttpResponses.error(exchange, 500, e.getMessage());
        }
    }

    /**
     * Gestisce la richiesta di impostazione nuovo ruolo per un utente.
     * @throws Exception
     */
    public void setRole() throws Exception{
            try {
                Map<String, Object> data = request.getBodyStreamData();
                if(data.get("role") == null || data.get("id") == null)
                    HttpResponses.error(exchange, 404, "Dati mancanti");
                RegisteredUser user = this.service.setRole(data);
                if(user == null)
                    HttpResponses.error(exchange, 404, "Modifica fallita");
                else
                    HttpResponses.success(exchange, DataMapper.mapObjectToJson(user));
            } catch (Exception e) {
                    HttpResponses.error(exchange, 500, e.getMessage());
            }
    }

}
