package org.repositories;

import org.httpServer.DbUtilities;
import org.models.users.RegisteredUser;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class RegisteredUserRepository extends Repository<RegisteredUser> {


    public RegisteredUserRepository() {
        super("users");
    }

    /**
     * Ritorna il record dell'utente in base all'username
     * @param username String il nome utente
     * @return Map<String, Object> userData i dati dell'utente
     * @throws Exception
     */
    public  Map<String, Object> getByUsername(String username) throws Exception {
            String query = "SELECT * FROM " + this.tableName + " WHERE username = ? OR email = ?";
            List<Map<String, Object>> data = DbUtilities.executeSelectQuery(query, new Object[]{username, username});
            System.out.println(data.toString());
            if (!data.isEmpty()) {
                return data.get(0);
            } else
                return null;
    }

    /**
     * Ritorna il record dell'utente in base all'access token
     * @param token String il token di accesso
     * @return List<Map<String, Object>> userData i dati dell'utente
     * @throws Exception
     */
    public  Map<String, Object> getByAccessToken(String token) throws Exception {
        String query = "SELECT * FROM " + this.tableName + " WHERE access_token = ?";
        List<Map<String, Object>> data =  DbUtilities.executeSelectQuery(query, new Object[]{token});
        if (!data.isEmpty()) {
            return data.get(0);
        } else
            return null;
    }

    /**
     * Inserisce il token nello strato di persistenza per
     * utilizzi futuri legati all'autenticazione.
     * @return true se il token viene salvato correttamente.
     */
    public int saveAccessToken(String token, String username) throws Exception {
        String query = "UPDATE " + this.tableName + " SET access_token = ? WHERE username = ?";
        return DbUtilities.executeQuery(query, new Object[]{token, username});
    }

    /**
     * Crea un nuovo utente registrato
     * @param user RegisteredUser l'utente da creare
     * @return RegisteredUser l'utente creato
     * @throws Exception
     */
    @Override
    public RegisteredUser create(RegisteredUser user, String query) throws Exception {
        query = "INSERT INTO "
                + this.tableName +
                " (id, username, email, password, salt, role) VALUES (?, ?, ?, ?, ?, ?)";
        super.create(user, query);
        return user;
    }

    /**
     * Aggiorna il ruolo di un utente esistente
     * @param user RegisteredUser l'utente da aggiornare con il nuovo ruolo
     * @return RegisteredUser l'utente aggiornato
     * @throws SQLException
     */
    public RegisteredUser setRole(RegisteredUser user) throws SQLException {
        long id = user.getId();
        String role = user.getRole();
        Object[] data = new Object[]{role, id};
        String query = "UPDATE " + this.tableName + " SET role = ? WHERE id = ?";

        int result = DbUtilities.executeQuery(query, data);
        if(result == 0)
            return null;
        return user;
    }
}
