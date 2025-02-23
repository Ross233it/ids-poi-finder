package org.poifinder.httpServer.auth;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import org.poifinder.models.users.RegisteredUser;
import org.poifinder.repositories.RegisteredUserRepository;
import org.poifinder.services.RegisteredUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Optional;

/**
 * Questa classe ha la responsabilità di consentire la valutazione dei
 * permessi di un utente in rapporto al suo ruolo
 */
@Getter
@Service
public class AuthMiddleware {


    /**
     * La lista dei livelli di permesso
     */
    private HashMap<Integer,String[]>permissions;

    @Autowired
    private RegisteredUserRepository userRepository;

    @Autowired
    public AuthMiddleware(RegisteredUserService userService) {
        this.permissions = new HashMap<>();
        this.permissionsInit();
    }

    /**
     * Inizializza i livelli di permessi disponibili a sistema.
     */
    private void permissionsInit(){
        this.permissions.put(1, new String[] {"platformAdmin"});
        this.permissions.put(2, new String[] {"platformAdmin", "animator"});
        this.permissions.put(3, new String[] {"platformAdmin", "animator", "authContributor"});
        this.permissions.put(4, new String[] {"platformAdmin", "animator", "authContributor","contributor"});
        this.permissions.put(5, new String[] {"public", "platformAdmin", "animator", "authContributor", "contributor"});
    }

    /**
     * Recupera e ritorna l'utente autenticato se presente, null altrimenti
     * @return RegisteredUser l'utente correntemente autenticato.
     */
    public RegisteredUser getCurrentUser() {
            return UserContext.getCurrentUser();
    }


    /**
     * Definisce l'utente corrente nel contesto di utilizzo
     * @param request HttpServletRequest la richiesta http
     */
    public void setCurrentUser(HttpServletRequest request){
        String accessToken = AuthUtilities.getAccessToken(request);

        Optional<RegisteredUser> currentUser = userRepository.getByAccessTokenParam(accessToken);
        if(currentUser.isPresent())
            UserContext.setCurrentUser(currentUser.orElse(null));
        else
            UserContext.setCurrentUser(null);
    }

    /**
     * Ritorna il ruolo/permesso dell'utente correntemente autenticato.
     * Ritorna public se non autenticato.
     * @return
     */
    private String getUserRole(){
        RegisteredUser currentUser = UserContext.getCurrentUser();
        if(currentUser != null)
            return currentUser.getRole();
        return "public";
    }

    /**
     * Verifica se l'utente dispone di un livello di autorizzazione
     * corrispondente a quello richiesto come parametro.
     * @param requiredPermission
     * @return
     */
    public  boolean hasPermissions(int requiredPermission){
        String currentUserRole = this.getUserRole();
        String[] authorizedRoles = this.permissions.get(requiredPermission);
        if (authorizedRoles != null) {
            for (String role : authorizedRoles) {
                if (role.equals(currentUserRole)) {
                    return true;
                }
            }
        }
        return false;
    }
}
