package org.controllers;

import org.httpServer.HttpResponses;
import org.httpServer.HttpUtilities;
import org.models.users.RegisteredUser;

import org.services.RegisteredUserService;

import java.io.IOException;
import java.util.Map;

/**
 * Questa classe ha la responsabilità di gestire le chiamate e le risposte
 * da e verso una interfaccia utente tramite Api e protocollo http.
 */
public class RegisteredUserController extends Controller<RegisteredUser, RegisteredUserService> {


    public RegisteredUserController() {
        super(new RegisteredUserService());
    }

    /**
     * Gestisce la richiesta di login
     * @throws IOException
     */
    public void login() throws Exception {
        try {
            Map<String, Object> data = HttpResponses.getStreamData(this.httpRequestHandler.getExchange());
            String accessToken =  this.service.login(data);
            if(accessToken == null || accessToken == "")
                HttpResponses.error(this.httpRequestHandler.getExchange(), 404, "Autenticazione fallita");
            else
                HttpResponses.success(this.httpRequestHandler.getExchange(), "{ accessToken: " + accessToken + " }" );
        } catch (Exception e) {
            HttpResponses.error(this.httpRequestHandler.getExchange(), 500, e.getMessage());
        }
    }

    /**
     * Gestisce la richiesta di logout
     * @throws Exception
     */
    public void logout() throws Exception{
        try {
            if(this.httpRequestHandler.getCurrentUser() != null){
                this.service.logout(this.httpRequestHandler.getCurrentUser());
                HttpResponses.success(this.httpRequestHandler.getExchange(), "Logout effettuato");
            }else
                HttpResponses.error(this.httpRequestHandler.getExchange(), 404, "Nessun utente loggato");
        } catch (Exception e) {
            HttpResponses.error(this.httpRequestHandler.getExchange(), 500, e.getMessage());
        }
    }

    /**
     * Gestisce la richiesta di impostazione nuovo ruolo per un utente.
     * @throws Exception
     */
    public void setRole() throws Exception{
        if(this.httpRequestHandler.getCurrentUser() != null && this.httpRequestHandler.getCurrentUser().hasRole("platformAdmin")){
            try {
                Map<String, Object> data = HttpResponses.getStreamData(this.httpRequestHandler.getExchange());
                if(data.get("role") == null || data.get("id") == null)
                    HttpResponses.error(this.httpRequestHandler.getExchange(), 404, "Dati mancanti");
                RegisteredUser user = this.service.setRole(data);
                if(user == null)
                    HttpResponses.error(this.httpRequestHandler.getExchange(), 404, "Modifica fallita");
                else
                    HttpResponses.success(this.httpRequestHandler.getExchange(), HttpResponses.objectToJson(user));
            } catch (Exception e) {
                    HttpResponses.error(this.httpRequestHandler.getExchange(), 500, e.getMessage());
            }
        }else
            HttpResponses.error(this.httpRequestHandler.getExchange(), 401, "Operazione non autorizzata");
    }

    /**
     * Gestisce la richiesta di eliminazione di un utente
     * @throws IOException
     */
    public void delete() throws IOException {
        try {
            int id = HttpUtilities.getQueryId(this.httpRequestHandler.getRequestPath());
            if(id > 0) {
                RegisteredUser deleted = this.service.delete(id);
                if(deleted != null)
                    HttpResponses.success(this.httpRequestHandler.getExchange(), "Utente eliminato");
                else
                    HttpResponses.error(this.httpRequestHandler.getExchange(), 404, "Utente non trovato");
            }
            else
                HttpResponses.error(this.httpRequestHandler.getExchange(), 404, "Id non valido");
        } catch (Exception e) {
            HttpResponses.error(this.httpRequestHandler.getExchange(), 500, e.getMessage());
        }
    }
}
