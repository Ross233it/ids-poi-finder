package org.poifinder.services;

import org.poifinder.dataMappers.RegisteredUserMapper;
import org.poifinder.httpServer.auth.AuthUtilities;
import org.poifinder.models.municipalities.Municipality;
import org.poifinder.models.users.RegisteredUser;
import org.poifinder.repositories.RegisteredUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Ha la responsabilità di gestire la logica di business connessa alla
 * manipolazione ed all'interazione con gli oggetti di tipo POI.
 */
@Service
public class RegisteredUserService  extends BaseService<RegisteredUser>{

    @Autowired
    public RegisteredUserService(RegisteredUserRepository repository,
                                 RegisteredUserMapper mapper) {
        super(repository, mapper);
    }

    /**
     * Crea un nuovo poi partendo da una serie di dati già validati
     * fornisce una password criptata e un salt per i futuri token di autenticazione.
     * @param objectData
     * @return
     */
    public RegisteredUser register(Map<String, Object> objectData){
//        String salt = AuthUtilities.generateSalt();
//        objectData.put("method", "insert");
//        objectData.put("salt", salt);
//        objectData.put("password", AuthUtilities.hashPassword((String) objectData.get("password"), salt));
//
//        RegisteredUser user = (RegisteredUser) this.mapper.mapDataToObject(objectData);
//        try {
//            if(objectData.get("municipality_id") != null && !objectData.get("role").equals("contributor")){
//                MunicipalityService service = new MunicipalityService();
//                try {
//                    Municipality municipality = service.getObjectById((Integer) objectData.get("municipality_id"));
//                    user.setMunicipality(municipality);
//                } catch (Exception e) {
//                    throw new RuntimeException(e);
//                }
//            }
//            this.repository.create(user, "");
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//        return user;
        return null;
    }

    /**
     * Verifica le credenziali di accesso di un utente e ritorna un token
     * di accesso in caso positivo.
     * @param data
     * @return
     */
    public String login(Map<String, Object> data) {
//        try {
//            String username = (String) data.get("username");
//            String password = (String) data.get("password");
//            Map<String, Object> userData = ((RegisteredUserRepository) this.repository).getByUsername(username);
//            if (userData == null)
//                return "";
//            if(AuthUtilities.verifyPassword(password, (String) userData.get("salt"), (String) userData.get("password"))){
//                String accessToken =  AuthUtilities.generateAccessToken(username);
//                ((RegisteredUserRepository) this.repository).saveAccessToken(accessToken, username);
//                return accessToken;
//            }else
//                return "";
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
        return "";
    }

    /**
     * Effettua il logout di un utente invalidando il token di accesso.
     * @param user
     */
    public void logout(RegisteredUser user) {
//        try {
//            user.setAccessToken(null);
//            ((RegisteredUserRepository) this.repository).saveAccessToken(null, user.getUsername());
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
    }

    /**
     * Recupera un utente con uno specifico access token.
     * @param token String il token di accesso
     * @return user se creato correttamente, null altrimenti
     */
    public RegisteredUser getByAccessToken(String token) {
//        return ((RegisteredUserRepository)this.repository).getByAccessToken(token).orElse(null);
//        try {
//            Map<String, Object> userData =  ((RegisteredUserRepository)this.repository).getByAccessToken(token);
//            if(userData == null)
//                return null;
//            RegisteredUser user = (RegisteredUser) this.mapper.mapDataToObject(userData);
//            user.setAccessToken(token);
//            user.setId((int)userData.get("id"));
//            return user;
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
        return null;
    }

    /**
     * Imposta un nuovo ruolo per un utente.
     * @param userId l'identificativo univoco dell'utente da aggiornare
     * @param newRole il nuovo ruolo da assegnare
     * @return RegisteredUser user con il nuovo ruolo
     */
    public RegisteredUser setRole(Long userId, String newRole) {
        RegisteredUser user = repository.findById(userId).orElse(null);
        if (user == null) {
            return null;
        }
        user.setRole(newRole);
        return repository.save(user);
    }

    //        try {
//            int userId = (int) data.get("id");
//            String newRole = (String) data.get("role");
//            RegisteredUser user = this.getObjectById(userId);
//            if(user == null)
//                return null;
//            user.setRole(newRole);
//            user.setId(userId);
//            ((RegisteredUserRepository) this.repository).setRole(userId);
//            return user;
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//}



    @Override
    public List<RegisteredUser> filter(Map<String, String> queryParams) throws Exception {
        return List.of();
    }

    @Override
    public RegisteredUser setStatus(Map<String, Object> data) throws Exception {
        return null;
    }

    @Override
    public RegisteredUser delete(long id) throws Exception {
        return null;
    }
}
