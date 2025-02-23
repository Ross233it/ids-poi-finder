package org.poifinder.repositories;

import org.poifinder.models.users.RegisteredUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.Optional;

@Repository
public interface RegisteredUserRepository extends JpaRepository<RegisteredUser, Long> {


    boolean existsByEmail(String email);

//    public RegisteredUserRepository() {
//        super("users");
//    }

    /**
     * Ritorna il record dell'utente in base all'username
     * @param username String il nome utente
     * @return Map<String, Object> userData i dati dell'utente
     * @throws Exception
     */
    Optional<RegisteredUser> findByUsername(String username);

    /**
     * Ritorna un utente in base all'access token
     * @param token String il token di accesso
     * @return List<Map<String, Object>> userData i dati dell'utente
     * @throws Exception
     */
    @Query("SELECT u FROM RegisteredUser u WHERE u.accessToken = :token")
    Optional<RegisteredUser> getByAccessTokenParam(@Param("token") String token);

     /**
     * Inserisce il token nello strato di persistenza per
     * utilizzi futuri legati all'autenticazione.
     * @return true se il token viene salvato correttamente.
     */
//    @Modifying
//    @Query("UPDATE RegisteredUser u SET u.accessToken = :token WHERE u.username = :username")
//    int saveAccessToken(@Param("token") String token, @Param("username") String username);

    /**
     * Crea un nuovo utente registrato
     * @param user RegisteredUser l'utente da creare
     * @return RegisteredUser l'utente creato
     * @throws Exception
     */
//    @Override
//    public RegisteredUser create(RegisteredUser user, String query) throws Exception {
//        query = "INSERT INTO "
//                + this.tableName +
//                " (id, username, email, password, salt, role, municipality_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
//        super.create(user, query);
//        return user;
//    }

    /**
     * Aggiorna il ruolo di un utente esistente
     * @param user RegisteredUser l'utente da aggiornare con il nuovo ruolo
     * @return RegisteredUser l'utente aggiornato
     * @throws SQLException
     */
//    @Modifying
//    @Query("UPDATE RegisteredUser u SET u.role = :role WHERE u.id = :id")
//    int setRole(@Param("role") String role, @Param("id") Long id);

//    public RegisteredUser setRole(RegisteredUser user) throws SQLException {
//        long id = user.getId();
//        String role = user.getRole();
//        Object[] data = new Object[]{role, id};
//        String query = "UPDATE " + this.tableName + " AS P SET role = ? WHERE id = ?";
//        int result = DbUtilities.executeQuery(query, data);
//        if(result == 0)
//            return null;
//        return user;
//    }
}
