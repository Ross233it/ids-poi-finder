package org.poifinder.dataMappers.users;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import org.antlr.v4.runtime.misc.NotNull;
import org.poifinder.dataMappers.DataMapper;
import org.poifinder.httpServer.auth.AuthUtilities;
import org.poifinder.models.users.RegisteredUser;
import org.springframework.stereotype.Component;

@Component
public class UserCreateMapper extends DataMapper<RegisteredUser> {
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

    private Long municipality_id;

    public UserCreateMapper(){}

    public UserCreateMapper(String username,
                            String email,
                            String password,
                            String role,
                            Long municipality_id) {
        this.username = username;
        this.email    = email;
        this.role = role;
        this.municipality_id = municipality_id;
        this.salt     = AuthUtilities.generateSalt();
        this.password = AuthUtilities.hashPassword(password, salt);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Long getMunicipality_id() {
        return municipality_id;
    }

    public void setMunicipality_id(Long municipality_id) {
        this.municipality_id = municipality_id;
    }
}