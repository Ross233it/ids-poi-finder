package org.controllers;

import org.httpServer.HttpResponses;
import org.models.users.IUser;
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

    public void setRole() throws Exception{
        try {
            Map<String, Object> data = HttpResponses.getStreamData(this.exchange);
            String newRole = ((RegisteredUserService) this.service).setRole(data);
            if(newRole == null || newRole == "")
                HttpResponses.error(this.exchange, 404, "Modifica fallita");
            else
                HttpResponses.success(this.exchange, newRole);
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
            case "GET":    this.index();  break;
            case "POST":
                if(this.requestPath.equals("/api/user/login")) {
                    try {
                        this.login(); break;
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
                if(this.requestPath.equals("/api/user/set-role")) {
                    try {
                        this.setRole(); break;
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }

                else
                    this.create(); break;
            case "PATCH":  this.update(); break;
            case "DELETE": this.delete(); break;
            default:       this.index();
        }
    }
}
