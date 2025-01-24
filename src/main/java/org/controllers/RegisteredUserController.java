package org.controllers;

import org.httpServer.HttpResponses;
import org.httpServer.HttpUtilities;
import org.models.users.IUser;
import org.models.users.RegisteredUser;

import org.services.RegisteredUserService;

import java.io.IOException;
import java.util.Map;

/**
 * Questa classe ha la responsabilità di gestire le chiamate e le risposte
 * da e verso una interfaccia utente tramite Api e protocollo http.
 */
public class RegisteredUserController extends Controller<IUser> {

    public RegisteredUserController() {
        super(new RegisteredUserService());
    }

    /**
     * Gestisce la richiesta di login
     * @throws IOException
     */
    public void login() throws Exception {
        try {
            Map<String, Object> data = HttpResponses.getStreamData(this.exchange);
            String accessToken = ((RegisteredUserService) this.service).login(data);
            if(accessToken == null || accessToken == "")
                HttpResponses.error(this.exchange, 404, "Autenticazione fallita");
            else
                HttpResponses.success(this.exchange, "{ accessToken: " + accessToken + " }" );
        } catch (Exception e) {
            HttpResponses.error(this.exchange, 500, e.getMessage());
        }
    }

    /**
     * Gestisce la richiesta di logout
     * @throws Exception
     */
    public void logout() throws Exception{
        try {
            if(this.currentUser != null){
                ((RegisteredUserService) this.service).logout(this.currentUser);
                HttpResponses.success(this.exchange, "Logout effettuato");
            }else
                HttpResponses.error(this.exchange, 404, "Nessun utente loggato");
        } catch (Exception e) {
            HttpResponses.error(this.exchange, 500, e.getMessage());
        }
    }

    /**
     * Gestisce la richiesta di impostazione nuovo ruolo per un utente.
     * @throws Exception
     */
    public void setRole() throws Exception{
        if(this.currentUser != null && this.currentUser.hasRole("platformAdmin")){
            try {
                Map<String, Object> data = HttpResponses.getStreamData(this.exchange);
                if(data.get("role") == null || data.get("id") == null)
                    HttpResponses.error(this.exchange, 404, "Dati mancanti");
                RegisteredUser user = ((RegisteredUserService) this.service).setRole(data);
                if(user == null)
                    HttpResponses.error(this.exchange, 404, "Modifica fallita");
                else
                    HttpResponses.success(this.exchange, HttpResponses.objectToJson(user));
            } catch (Exception e) {
                    HttpResponses.error(this.exchange, 500, e.getMessage());
            }
        }else
            HttpResponses.error(this.exchange, 401, "Operazione non autorizzata");
    }

    /**
     * Gestisce la richiesta di eliminazione di un utente
     * @throws IOException
     */

    public void delete() throws IOException {
        try {
            int id = HttpUtilities.getQueryId(this.requestPath);
            if(id > 0) {
                RegisteredUser deleted = ((RegisteredUserService) this.service).delete(id);
                if(deleted != null)
                    HttpResponses.success(this.exchange, "Utente eliminato");
                else
                    HttpResponses.error(this.exchange, 404, "Utente non trovato");
            }
            else
                HttpResponses.error(this.exchange, 404, "Id non valido");
        } catch (Exception e) {
            HttpResponses.error(this.exchange, 500, e.getMessage());
        }
    }


    /**
     * Gestisce i comportamenti attivati dalle chiamate su specifiche rotte http
     * @throws IOException
     */
    @Override
    protected void handleGetCalls() throws IOException {
        int id = HttpUtilities.getQueryId(this.requestPath);
        if (this.requestPath.equals("/api/user/set-role")
                && this.currentUser.hasRole("platformAdmin")) {
            try {
                this.setRole();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else if (id > 0)
            this.show(id);
        else
            this.index();
    }

    @Override
    protected void handlePostCalls()throws IOException{
        if(this.requestPath.equals("/api/user/login")) {
            try {
                this.login();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        else if(this.requestPath.equals("/api/user/logout")) {
            try {
                this.logout();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }else
            this.create();
    }

    @Override
    protected void handlePatchCalls() throws IOException{
        if(this.requestPath.equals("/api/user/set-role")) {
            try {
                this.setRole();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }else
            this.update();
    }
}
