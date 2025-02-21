package org.poifinder.models.users;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import org.antlr.v4.runtime.misc.NotNull;
import org.poifinder.httpServer.auth.AuthUtilities;
import org.poifinder.models.municipalities.Municipality;


/**
 * Questa classe rappresenta un utente registrato all'interno del sistema
 */
@Entity
@Table(name="users")
public class RegisteredUser implements IUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String username;

    @NotNull
    @Email(message = "Fornire un indirizzo email valido")
    private String email;

    @NotNull
    private String password;

    private String salt = null;

    private String role = null;

    @Column(name = "access_token")
    private  String accessToken = null;

    @OneToOne
    private Municipality municipality;

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

    public Municipality getMunicipality() { return municipality; }

    /**
     * Verifica se un utente ha un determinato ruolo
     * @param role
     * @return
     */
    public Boolean hasRole(String role) {
        return this.role.equals(role);
    }


    /** SETTERS **/

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
    public void setMunicipality(Municipality municipality) { this.municipality = municipality; }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }
}
