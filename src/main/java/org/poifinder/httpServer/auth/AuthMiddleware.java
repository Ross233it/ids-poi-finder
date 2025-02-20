package org.poifinder.httpServer.auth;

import com.sun.net.httpserver.HttpExchange;
import org.poifinder.models.users.RegisteredUser;
import org.poifinder.services.RegisteredUserService;

import java.util.HashMap;

/**
 * Questa classe ha la responsabilità di consentire la valutazione dei
 * permessi di un utente in rapporto al suo ruolo
 */

/** DEPRECATO - classe necessaria solo per la versione JAVA **/
//TODO da rimuovere
public class AuthMiddleware {

    /**
     * La lista dei livelli di permesso
     */
    private HashMap<Integer,String[]>permissions;


    public AuthMiddleware(){
        this.permissions = new HashMap<Integer, String[]>();
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
     * Ritorna i livelli di privilegio disponibili a sistema. - Getter
     * @return
     */
    public HashMap<Integer,String[]> getPermissions(){
        return this.permissions;
    }


    /**
     * Recupera e ritorna l'utente autenticato se presente, null altrimenti
     * @param exchange
     * @return RegisteredUser l'utente correntemente autenticato.
     */
    public RegisteredUser getCurrentUser(HttpExchange exchange) {
//        String accessToken = AuthUtilities.getAccessToken(exchange);
//        if (accessToken != null && accessToken != "") {
//            RegisteredUserService userService = new RegisteredUserService();
//            RegisteredUser currentUser = userService.getByAccessToken(accessToken);
//            if (currentUser != null)
//                return currentUser;
//        }
        return null;
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
    public boolean hasPermissions(int requiredPermission){
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
