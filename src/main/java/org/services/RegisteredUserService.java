package org.services;

import org.dataMappers.RegisteredUserMapper;
import org.httpServer.auth.AuthUtilities;
import org.models.municipalities.Municipality;
import org.models.users.RegisteredUser;
import org.repositories.RegisteredUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Ha la responsabilità di gestire la logica di business connessa alla
 * manipolazione ed all'interazione con gli oggetti di tipo POI.
 */
@Service
public class RegisteredUserService extends BaseService<RegisteredUser> {

    @Autowired
    public RegisteredUserService(RegisteredUserRepository repository,
                                 RegisteredUserMapper mapper) {
        super(repository, mapper);
    }

    public RegisteredUserService() {
        super(new RegisteredUserRepository(), new RegisteredUserMapper());
    }


    /**
     * Crea un nuovo poi partendo da una serie di dati già validati
     * fornisce una password criptata e un salt per i futuri token di autenticazione.
     * @param objectData
     * @return
     */
    public RegisteredUser register(Map<String, Object> objectData){
        String salt = AuthUtilities.generateSalt();
        objectData.put("method", "insert");
        objectData.put("salt", salt);
        objectData.put("password", AuthUtilities.hashPassword((String) objectData.get("password"), salt));

        RegisteredUser user = (RegisteredUser) this.mapper.mapDataToObject(objectData);
        try {
            if(objectData.get("municipality_id") != null && !objectData.get("role").equals("contributor")){
                MunicipalityService service = new MunicipalityService();
                try {
                    Municipality municipality = service.getObjectById((Integer) objectData.get("municipality_id"));
                    user.setMunicipality(municipality);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            this.repository.create(user, "");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return user;
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
            if (userData == null)
                return "";
            if(AuthUtilities.verifyPassword(password, (String) userData.get("salt"), (String) userData.get("password"))){
                String accessToken =  AuthUtilities.generateAccessToken(username);
                ((RegisteredUserRepository) this.repository).saveAccessToken(accessToken, username);
                return accessToken;
            }else
                return "";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Effettua il logout di un utente invalidando il token di accesso.
     * @param user
     */
    public void logout(RegisteredUser user) {
        try {
            user.setAccessToken(null);
            ((RegisteredUserRepository) this.repository).saveAccessToken(null, user.getUsername());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Recupera un utente con uno specifico access token.
     * @param token String il token di accesso
     * @return user se creato correttamente, null altrimenti
     */
    public RegisteredUser getByAccessToken(String token) {
        try {
            Map<String, Object> userData =  ((RegisteredUserRepository)this.repository).getByAccessToken(token);
            if(userData == null)
                return null;
            RegisteredUser user = (RegisteredUser) this.mapper.mapDataToObject(userData);
            user.setAccessToken(token);
            user.setId((int)userData.get("id"));
            return user;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Imposta un nuovo ruolo per un utente.
     * @param data informazioni per l'aggiornamento del ruolo
     * @return RegisteredUser user con il nuovo ruolo
     */
    public RegisteredUser setRole(Map<String, Object> data){
        try {
            int userId = (int) data.get("id");
            String newRole = (String) data.get("role");
            RegisteredUser user = this.getObjectById(userId);
            if(user == null)
                return null;
            user.setRole(newRole);
            user.setId(userId);
            ((RegisteredUserRepository) this.repository).setRole(user);
            return user;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Imposta un nuovo ruolo per un utente.
     * @param id informazioni per l'aggiornamento del ruolo
     * @return RegisteredUser user con il nuovo ruolo
     */
    public RegisteredUser delete(int id) throws Exception {
        try {
            RegisteredUser user = this.getObjectById(id);
            if(user == null)
                return null;
            user.setId(id);
            this.repository.delete(user, null);
            return user;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
