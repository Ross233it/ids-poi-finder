package org.poifinder.controllers;

import org.poifinder.dataMappers.users.UserCreateMapper;
import org.poifinder.dataMappers.users.UserLoginMapper;
import org.poifinder.httpServer.auth.UserContext;
import org.poifinder.models.users.RegisteredUser;

import org.poifinder.services.RegisteredUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * Questa classe ha la responsabilità di gestire le chiamate e le risposte
 * da e verso una interfaccia utente tramite Api e protocollo http.
 */
@RestController
@RequestMapping("api/user")
public class RegisteredUserController extends BaseController<RegisteredUser> {

    private final RegisteredUserService registeredUserService;

    @Autowired
    public RegisteredUserController(RegisteredUserService userService, RegisteredUserService registeredUserService) {
        super(userService);
        this.registeredUserService = registeredUserService;
    }

    /**
     * Gestisce la richiesta di registrazione di un nuovo utente
     * @param mapper UserCreateMapper i dati dell'utente da registrare
     * @return ResponseEntity<String> messaggio di conferma o di errore
     * @throws IOException
     */
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserCreateMapper mapper) throws IOException {
        try {
            registeredUserService.register(mapper);
            return ResponseEntity.status(HttpStatus.CREATED).body("Utente registrato con successo");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ERROR - Si è verificato un problema durante la registrazione : " + e.getMessage());
        }
    }

    /**
     * Gestisce la richiesta di login
     * @throws IOException
     */
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserLoginMapper mapper) throws Exception {
        try {
            RegisteredUser currentUser = registeredUserService.login(mapper);
            UserContext.setCurrentUser(currentUser);
            return ResponseEntity.status(HttpStatus.CREATED).body("Utente autenticato con successo Token: " + currentUser.getToken());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ERROR - Si è verificato un problema durante la registrazione : " + e.getMessage());
        }
    }

    /**
     * Gestisce la richiesta di logout
     * @throws Exception
     */
    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String accessToken) throws Exception{
        try {
            RegisteredUser currentUser = registeredUserService.getByAccessToken(accessToken);
            if(currentUser != null) {
                this.registeredUserService.logout(currentUser);
                UserContext.setCurrentUser(currentUser);
                UserContext.clear();
                return ResponseEntity.status(HttpStatus.CREATED).body("Utente disconnesso con successo" );
            }
        }catch(Exception e){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ERROR - Si è verificato un problema - utente non autenticato");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Non è stato possibile completare la procedura di logout");
    }

    /**
     * Gestisce la richiesta di impostazione nuovo ruolo per un utente.
     * @throws Exception
     */
    public void setRole() throws Exception{
//            try {
//                Map<String, Object> data = request.getBodyStreamData();
//                if(data.get("role") == null || data.get("id") == null)
//                    CustomResponse.error(exchange, 404, "Dati mancanti");
//                RegisteredUser user = this.service.setRole(data);
//                if(user == null)
//                    CustomResponse.error(exchange, 404, "Modifica fallita");
//                else
//                    CustomResponse.success(exchange, DataMapper.mapObjectToJson(user));
//            } catch (Exception e) {
//                    CustomResponse.error(exchange, 500, e.getMessage());
//            }
    }

}
