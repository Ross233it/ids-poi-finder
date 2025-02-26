package org.poifinder.repositories;

import org.poifinder.models.users.RegisteredUser;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RegisteredUserRepository extends IRepository<RegisteredUser> {


    boolean existsByEmail(String email);

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
}
