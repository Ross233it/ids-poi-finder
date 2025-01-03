package org.controllers;

import org.httpServer.HttpResponses;
import org.httpServer.HttpUtilities;
import org.models.users.IUser;
import org.models.users.RegisteredUser;
import org.repositories.RegisteredUserRepository;
import org.services.RegisteredUserService;

import java.io.IOException;
import java.util.Map;

/**
 * Questa classe ha la responsabilità di gestire le chiamate e le risposte
 * da e verso una interfaccia utente tramite Api e protocollo http.
 */
public class RegisteredUserController extends Controller<IUser> {

    public RegisteredUserController() {
        super(new RegisteredUserService(new RegisteredUserRepository("users")));
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
                HttpResponses.success(this.exchange, accessToken );
        } catch (Exception e) {
            HttpResponses.error(this.exchange, 500, e.getMessage());
        }
    }

    /**
     * Gestisce la richiesta di impostazione nuovo ruolo per un utente.
     * @throws Exception
     */
    public void setRole() throws Exception{
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
    protected void handleRoutes(String method) throws IOException {
        switch (method) {
            case "GET":
                int id = HttpUtilities.getQueryId(this.requestPath);
                if(id > 0) {
                    try {
                        this.show(id);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
                else
                    this.index();
                break;
            case "POST":
                if(this.requestPath.equals("/api/user/login")) {
                    try {
                        this.login(); break;
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
                this.create(); break;

            case "PATCH":
                if(this.requestPath.equals("/api/user/set-role")) {
                    try {
                        this.setRole(); break;
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
                this.update(); break;
            case "DELETE": this.delete(); break;
            default:       this.index();
        }
    }
}
