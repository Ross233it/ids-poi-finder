package org.services;

import org.httpServer.AuthUtilities;
import org.models.users.RegisteredUser;
import org.models.users.IUser;
import org.repositories.RegisteredUserRepository;

import java.util.Map;

/**
 * Ha la responsabilità di gestire la logica di business connessa alla
 * manipolazione ed all'interazione con gli oggetti di tipo POI.
 */

public class RegisteredUserService extends Service<IUser> {


    public RegisteredUserService(RegisteredUserRepository repository) {
       super(repository);
    }


    @Override
    public String index() {
        return "";
    }


    /**
     * Crea un nuovo poi partendo da una serie di dati già validati
     * @param objectData
     * @return
     */
    public IUser create(Map<String, Object> objectData){

        String salt = AuthUtilities.generateSalt();
        objectData.put("password", AuthUtilities.hashPassword((String) objectData.get("password"), salt));

        RegisteredUser user = new RegisteredUser(
                (String)  objectData.get("username"),
                (String)  objectData.get("email"),
                (String)  objectData.get("password")
        );
        user.setRole("contributor");
        user.setSalt(salt);

        try {
            this.repository.create(user);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println(objectData);
        return user;
    }

    @Override
    public String getById(String id) {
        return "";
    }

    /**
     * Verifica le credenziali di accesso di un utente e ritorna un token
     * di accesso in caso positivo.
     * @param data
     * @return
     */
    public String login(Map<String, Object> data) {
        try {
            String username = (String) data.get("username");
            String password = (String) data.get("password");
            Map<String, Object> userData = ((RegisteredUserRepository) this.repository).getByUsername(username);
            if(AuthUtilities.verifyPassword(password, (String) userData.get("salt"), (String) userData.get("password"))){
                String accessToken =  AuthUtilities.generateAccessToken(username);
                ((RegisteredUserRepository) this.repository).saveAccessToken(accessToken, username);
                return accessToken;
            }
            else
                return "";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
