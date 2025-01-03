package org.repositories;

import org.httpServer.DbUtilities;
import org.models.users.IUser;
import org.models.users.RegisteredUser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class RegisteredUserRepository extends Repository<RegisteredUser> {


    public RegisteredUserRepository(String tableName) {
        super(tableName);
    }

    //todo rifattorizzare le query nel repository principale
    /**
     * Ritorna il record dell'utente in base all'username
     * @param username String il nome utente
     * @return Map<String, Object> userData i dati dell'utente
     * @throws Exception
     */
    public  Map<String, Object> getByUsername(String username) throws Exception {
            String query = "SELECT * FROM " + this.tableName + " WHERE username = ? OR email = ?";
            Connection connection = dbConnectionManager.openConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, username);
            ResultSet resultSet = preparedStatement.executeQuery();

            List<Map<String, Object>> data = DbUtilities.mapDbDataToList(resultSet);
            if(!data.isEmpty()){
                Map<String, Object> userData = (Map<String, Object>) data.get(0);
                connection.close();
                return userData;
            }
            else
                return null;
    }

    /**
     * Inserisce il token nello strato di persistenza per
     * utilizzi futuri legati all'autenticazione.
     * @return true se il token viene salvato correttamente.
     */
    public boolean saveAccessToken(String token, String username) throws Exception {
        String query = "UPDATE " + this.tableName + " SET access_token = ? WHERE username = ?";
        Connection connection = dbConnectionManager.openConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, token);
        preparedStatement.setString(2, username);
        int result = preparedStatement.executeUpdate();
        return result > 0;
    }

    /**
     * Crea un nuovo utente registrato
     * @param user RegisteredUser l'utente da creare
     * @return RegisteredUser l'utente creato
     * @throws Exception
     */
    @Override
    public RegisteredUser create(RegisteredUser user) throws Exception {
        if (user == null) {
            throw new IllegalArgumentException("L'entity non può essere null.");
        }
        List<String> columns = Arrays.asList("id", "username", "email", "password", "salt","role");
        Object[] data = user.getData();
        super.insert(columns, data);
        return user;
    }

    /**
     * Aggiorna il ruolo di un utente esistente
     * @param user RegisteredUser l'utente da aggiornare con il nuovo ruolo
     * @return RegisteredUser l'utente aggiornato
     * @throws SQLException
     */
    public RegisteredUser setRole(RegisteredUser user) throws SQLException {
        int id = user.getId();
        String role = user.getRole();
        Object[] data = new Object[]{role, id};
        String query = "UPDATE " + this.tableName + " SET role = ? WHERE id = ?";
        DbUtilities.executeQuery(query, data);
        return user;
    }
}
