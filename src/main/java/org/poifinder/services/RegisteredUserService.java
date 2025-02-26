package org.poifinder.services;

import jakarta.transaction.Transactional;
import org.poifinder.dataMappers.users.UserCreateMapper;
import org.poifinder.dataMappers.users.UserLoginMapper;
import org.poifinder.dataMappers.users.UserUpdateMapper;
import org.poifinder.httpServer.auth.AuthUtilities;
import org.poifinder.httpServer.auth.UserContext;
import org.poifinder.models.municipalities.Municipality;
import org.poifinder.models.users.RegisteredUser;
import org.poifinder.repositories.MunicipalityRepository;
import org.poifinder.repositories.RegisteredUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
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
    PoiService poiService;

    @Autowired
    ActivityService activityService;

    @Autowired
    private MunicipalityRepository municipalityRepository;

    @Autowired
    public RegisteredUserService(RegisteredUserRepository repository){
        super(repository);
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
        newUser.setRole("contributor");
        userRepository.save(newUser);
    }


    /**
     * Modifica le informazioni di un utente in base a dati ricevuti
     * @param id l'id dell'utente da modificare
     * @param mapper le informazioni aggiornate
     * @return RegisteredUser utente aggiornato.
     * @throws Exception
     */
    public RegisteredUser update(Long id, UserUpdateMapper mapper) throws Exception {
        RegisteredUser existingUser = userRepository.findById(id).orElse(null);
        if (existingUser == null) {
            throw new RuntimeException("Utente non trovato");
        }
        if (!existingUser.getEmail().equals(mapper.getEmail()) && userRepository.existsByEmail(mapper.getEmail())) {
            throw new RuntimeException("Email già registrata");
        }

        if (mapper.getMunicipality_id() != null && !mapper.getMunicipality_id().equals(existingUser.getMunicipality().getId())) {
            Municipality municipality = municipalityRepository.getObjectById(mapper.getMunicipality_id());
            if (municipality == null) {
                throw new RuntimeException("Comune non valido o non trovato");
            }
            existingUser.setMunicipality(municipality);
        }
        String salt = existingUser.getSalt();
        String newPassword = AuthUtilities.hashPassword(mapper.getPassword(), salt);
        existingUser.setPassword(newPassword);
        existingUser.setUsername(mapper.getUsername());
        existingUser.setEmail(mapper.getEmail());
        existingUser.setRole(mapper.getRole());
        userRepository.save(existingUser);
        return existingUser;
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
        Optional<RegisteredUser> currentUser = userRepository.getByAccessTokenParam(token);
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
        RegisteredUser savedUser = repository.save(user);
        if(savedUser != null){
            Map<String, Object>userData = new HashMap<>();
            userData.put("Utente Modificato", savedUser);
            this.eventManager.notify("new-role-notification", userData);
        }
        return savedUser;
    }


    @Override
    public RegisteredUser setStatus(Long id, String status) throws Exception {
       return null;
    }
    /**
     * Rimuove un utente e riassegna tutti i suoi contenuti ad un utente di
     * default
     * @param id dell'utente da rimuovere
     * @return l'utente di default riassegnatario.
     * @throws Exception
     */
    @Override
    @Transactional
    public RegisteredUser delete(Long id) throws Exception {
        RegisteredUser currentUser = UserContext.getCurrentUser();
        if (currentUser.getId() == id || currentUser.hasRole("platformAdmin")) {
            RegisteredUser userToDelete = userRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Utente non trovato"));

            RegisteredUser newUser = userRepository.findById(1L)
                    .orElseThrow(() -> new RuntimeException("Utente sostitutivo non trovato"));

            activityService.setAuthorAndApproverMassively(userToDelete, newUser);
            poiService.setAuthorAndApproverMassively(userToDelete, newUser);
            userRepository.delete(userToDelete);
            return newUser;
        }
        return null;
    }
}
