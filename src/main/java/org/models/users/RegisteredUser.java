package org.models.users;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.httpServer.auth.AuthUtilities;
import org.models.Content;


/**
 * Questa classe rappresenta un utente registrato all'interno del sistema
 */
@Entity
@Table(name="users")
public class RegisteredUser extends Content implements IUser {

    private String username;

    private String email;

    private String password;

    private String salt = null;

    private String role = null;

    public  String accessToken = null;


    public RegisteredUser(String username, String email,  String role) {
        this.username = username;
        this.email    = email;
        this.role     = role;
    }

    public RegisteredUser() {

    }

    @Override
    public String toString() {
        return "{"
                + "\"id\":\"" + getId() + "\","
                + "\"username\":\"" + username + "\","
                + "\"email\":\"" + email + "\","
                + "\"role\":\"" + role + "\","
                + "}";
    }

    /** getters **/
    public String getUsername() { return username; }
    public String getEmail()    { return email; }
    public String getPassword() { return password; }
    public String getRole()     { return role; }
    public String getSalt()     { return salt; }
    public String getToken()    { return this.accessToken; }
    public Object[]getData()    { return new Object[] {
            this.getId(),
            this.getUsername(),
            this.getEmail(),
            this.getPassword(),
            this.getSalt(),
            this.getRole()
        };
    }

    /**
     * Verifica se un utente ha un determinato ruolo
     * @param role
     * @return
     */
    public Boolean hasRole(String role) {
        return this.role.equals(role);
    }


    /** setters **/

    /**
     * Imposta il token di accesso per l'utente
     * @param token
     */
    public void setAccessToken(String token){
        if(token != null)
            this.accessToken = token;
        else
            this.accessToken = AuthUtilities.generateAccessToken(this.username);
    };

    public void setSalt(String salt)         { this.salt = salt; }
    public void setUsername(String username) { this.username = username; }
    public void setEmail(String email)       { this.email = email; }
    public void setPassword(String password) { this.password = password; }
    public void setRole(String role)         { this.role = role; }
}
