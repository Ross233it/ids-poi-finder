package org.httpServer;

import com.sun.net.httpserver.HttpExchange;
import org.models.users.RegisteredUser;
import org.repositories.RegisteredUserRepository;
import org.services.RegisteredUserService;

import java.util.HashMap;

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
     * Verifica se l'utente dispone di un livello di autorizzazione
     * corrispondente a quello richiesto come parametro.
     * @param exchange
     * @param requiredPermission
     * @return
     */
    public boolean hasPermissions(HttpExchange exchange, int requiredPermission){
        String currentUserRole = this.getUserRole(exchange);
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

    /**
     * Ritorna il ruolo/permesso dell'utente correntemente autenticato.
     * Ritorna public se non autenticato.
     * @param exchange
     * @return
     */
    private String getUserRole(HttpExchange exchange){
        String method = exchange.getRequestMethod();
        String accessToken = AuthUtilities.getAccessToken(exchange);
        if(accessToken != null && accessToken != ""){
            RegisteredUserRepository repository = new RegisteredUserRepository();
            RegisteredUserService userService = new RegisteredUserService();
            RegisteredUser currentUser = userService.getByAccessToken(accessToken);
            if(currentUser != null)
                return currentUser.getRole();
        }
            return "public";
    }
}
