package org.poifinder.controllers;

import org.poifinder.dataMappers.DataMapper;
import org.poifinder.dataMappers.Views;
import org.poifinder.dataMappers.users.UserCreateMapper;
import org.poifinder.dataMappers.users.UserLoginMapper;
import org.poifinder.dataMappers.users.UserUpdateMapper;
import org.poifinder.httpServer.auth.UserContext;
import org.poifinder.models.users.RegisteredUser;

import org.poifinder.repositories.RegisteredUserRepository;
import org.poifinder.services.RegisteredUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.annotation.JsonView;

import java.io.IOException;

/**
 * Questa classe ha la responsabilità di gestire le chiamate e le risposte
 * da e verso una interfaccia utente tramite Api e protocollo http.
 */
@RestController
@RequestMapping("api/user")
public class RegisteredUserController extends BaseController<RegisteredUser> {

    private final RegisteredUserService registeredUserService;
    private final RegisteredUserRepository registeredUserRepository;


    @Autowired
    public RegisteredUserController(RegisteredUserService userService, RegisteredUserService registeredUserService, RegisteredUserRepository registeredUserRepository) {
        super(userService);
        this.registeredUserService = registeredUserService;
        this.registeredUserRepository = registeredUserRepository;
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
            return ResponseEntity.status(HttpStatus.CREATED).body("Utente autenticato con successo Token: " + currentUser.getAccessToken());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ERROR - Si è verificato un problema durante la registrazione : " + e.getMessage());
        }
    }


    /**
     * Aggiorna un utente esistente
     * @param id l'id dell'utente da aggiornare
     * @param entityData dati sull'utente
     * @return
     * @throws Exception
     */
    @PatchMapping("/{id}/update")
    @JsonView(Views.Public.class)
    public ResponseEntity<RegisteredUser> update(@PathVariable Long id,
                                                 @RequestBody UserUpdateMapper entityData)
                                                 throws Exception{
        RegisteredUser currentUser = UserContext.getCurrentUser();

        if(currentUser.getId() == id || currentUser.getRole().equals("platformAdmin")){
            try {
                RegisteredUser updatedEntity = registeredUserService.update(id, entityData);
                if(updatedEntity != null )
                    return ResponseEntity.status(HttpStatus.OK).body(updatedEntity);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
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


    public RegisteredUser getObjectById(Long id){
        return registeredUserRepository.getById(id);
    }


    /**
     * Gestisce la richiesta di eleiminazione di un utente.
     * @throws Exception
     */
    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) throws RuntimeException{
        RegisteredUser currentUser = UserContext.getCurrentUser();
        if(currentUser != null &&
            (currentUser.getId() == id ||
             currentUser.hasRole("platformAdmin"))){
            try {
                service.delete(id);
                return ResponseEntity.status(HttpStatus.CREATED).body("Utente eliminato con successo");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("Eliminazione non possibile o non consentita");
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
