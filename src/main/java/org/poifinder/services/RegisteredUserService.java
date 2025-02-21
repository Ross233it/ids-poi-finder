package org.poifinder.services;

import org.poifinder.dataMappers.users.UserCreateMapper;
import org.poifinder.dataMappers.users.UserLoginMapper;
import org.poifinder.httpServer.auth.AuthUtilities;
import org.poifinder.models.municipalities.Municipality;
import org.poifinder.models.users.RegisteredUser;
import org.poifinder.repositories.MunicipalityRepository;
import org.poifinder.repositories.RegisteredUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Ha la responsabilità di gestire la logica di business connessa alla
 * manipolazione ed all'interazione con gli oggetti di tipo POI.
 */
@Service
public class RegisteredUserService  extends BaseService<RegisteredUser>{

    @Autowired
    RegisteredUserRepository userRepository;

    @Autowired
    private MunicipalityRepository municipalityRepository;

    @Autowired
    public RegisteredUserService(RegisteredUserRepository repository,
                                 UserCreateMapper mapper) {
        super(repository, mapper);
    }

    /**
     * Crea un nuovo poi partendo da una serie di dati già validati
     * fornisce una password criptata e un salt per i futuri token di autenticazione.
     * @param mapper UserCreateMapper i dati dell'utente da registrare
     * @return
     */
    public void register(UserCreateMapper mapper){
        if (userRepository.existsByEmail(mapper.getEmail())) {
            throw new RuntimeException("Email già registrata");
        }
        Municipality municipality = municipalityRepository.getObjectById(mapper.getMunicipality_id());
        if(municipality == null){
            throw new RuntimeException("Comune non valido o non trovato");
        }
        RegisteredUser newUser = new RegisteredUser(
                mapper.getUsername(),
                mapper.getEmail(),
                mapper.getRole()
        );
        newUser.setSalt(AuthUtilities.generateSalt());
        newUser.setPassword(AuthUtilities.hashPassword(mapper.getPassword(), newUser.getSalt()));
        newUser.setMunicipality(municipality);
        newUser.setRole(mapper.getRole());
        userRepository.save(newUser);
    }

    /**
     * Verifica le credenziali di accesso di un utente e ritorna un token
     * di accesso in caso positivo.
     * @param mapper UserLoginMapper i dati di accesso dell'utent
     * @return
     */
    public RegisteredUser login(UserLoginMapper mapper) {
        try {
            String username = mapper.getUsername();
            String password = mapper.getPassword();
            RegisteredUser user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));;
            if(user==null){
              throw new RuntimeException("Nessun utente valido");
            }
            if(AuthUtilities.verifyPassword(password , user.getSalt(), user.getPassword() )){
                String accessToken =  AuthUtilities.generateAccessToken(username);
                user.setAccessToken(accessToken);
                userRepository.save(user);
                return user;
            }else
                throw new RuntimeException("La procedura di login non è stata completata");
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
            userRepository.save(user);
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
        token = token.replaceFirst("^Bearer\\s+", "");
        Optional<RegisteredUser> currentUser = userRepository.getByAccessToken(token);
        return currentUser.orElseThrow(()-> new RuntimeException("Utente non trovato per il token in oggetto"));
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
