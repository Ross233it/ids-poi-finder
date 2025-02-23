package org.poifinder.models.users;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;
import org.poifinder.dataMappers.Views;
import org.poifinder.httpServer.auth.AuthUtilities;
import org.poifinder.models.municipalities.Municipality;

import java.util.List;


/**
 * Questa classe rappresenta un utente registrato all'interno del sistema
 */
@Entity
@Table(name="users")
@Getter
@Setter
public class RegisteredUser implements IUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(Views.Public.class)
    private Long id;

    @NotNull
    @JsonView(Views.Public.class)
    private String username;

    @NotNull
    @JsonView(Views.Public.class)
    @Email(message = "Fornire un indirizzo email valido")
    private String email;

    @NotNull
    private String password;

    private String salt = null;

    private String role = null;

    @Column(name = "access_token")
    private  String accessToken = null;

    @ManyToOne
    @JsonView(Views.Public.class)
    @JoinColumn(name = "municipality_id", nullable = true, unique = false)
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


    /**
     * Verifica se un utente ha un determinato ruolo
     * @param role
     * @return
     */
    public Boolean hasRole(String role) {
        return this.role.equals(role);
    }

    /**
     * Verifica se un utente ha almeno un ruolo fra quelli richiesti
     * @param roles ArrayList i ruoli richiesti.
     * @return
     */
    public Boolean hasOneRole(List<String> roles) {
        return  roles.contains(role);
    }



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


}
