package org.poifinder.httpServer.auth;

import com.sun.net.httpserver.HttpExchange;
import jakarta.servlet.http.HttpServletRequest;
import org.poifinder.models.municipalities.Municipality;
import org.poifinder.models.users.RegisteredUser;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.List;
import java.util.Map;

/**
 * Classe di utilità per la gestione dell'autenticazione
 */

public class AuthUtilities {

    /**
     * Genera un salt casuale.
     * @return una stringa Base64 del salt generato.
     */
    public static String generateSalt() {
        try {
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            byte[] salt = new byte[16];
            random.nextBytes(salt);
            return Base64.getEncoder().encodeToString(salt);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Errore nella generazione del salt", e);
        }
    }

    /**
     * Genera un hash della password combinata con il salt.
     * @param password la password in chiaro.
     * @param salt il salt generato.
     * @return l'hash della password.
     */
    public static String hashPassword(String password, String salt) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            String saltedPassword = password + salt;
            byte[] hash = md.digest(saltedPassword.getBytes());
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Errore nella generazione dell'hash", e);
        }
    }

    /**
     * Verifica se la password inserita corrisponde all'hash salvato.
     * @param password la password in chiaro inserita dall'utente.
     * @param salt il salt associato alla password salvata.
     * @param storedHash l'hash salvato nel database.
     * @return true se la password corrisponde, altrimenti false.
     */
    public static boolean verifyPassword(String password, String salt, String storedHash) {
        String computedHash = hashPassword(password, salt);
        return computedHash.equals(storedHash);
    }

    /**
     * Genera un token di accesso per l'utente.
     * @param username il nome utente dell'utente.
     * @return il token di accesso generato.
     */
    public static String generateAccessToken(String username) {
        try {
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            byte[] token = new byte[16];
            random.nextBytes(token);
            return Base64.getEncoder().encodeToString(token);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Errore nella generazione del token", e);
        }
    }

    /**
     * Recupera l'access token dall'intestazione della chiamata http
     * @param request HttpServletRequest richiesta http
     */
    public static String getAccessToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String accessToken = authorizationHeader.substring(7);
            return accessToken;
        }
        return "";
    }

    /**
     * Verifica se l'utente corrente dispone dei permessi per interagire con
     * uno specifico comune
     * @param municipality_id l'id del comune con cui interagire
     * @return
     */
    public static boolean canUserHandleMunicipality(Long municipality_id){
        RegisteredUser currentUser = UserContext.getCurrentUser();
        long userMunicipalityId = 0;
        if(currentUser != null && currentUser.getMunicipality() != null) {
            Municipality municipality = currentUser.getMunicipality();
            userMunicipalityId = municipality.getId();
        }
        return  municipality_id == userMunicipalityId;
    };


}
