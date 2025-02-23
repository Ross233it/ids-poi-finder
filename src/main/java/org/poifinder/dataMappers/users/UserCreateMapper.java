package org.poifinder.dataMappers.users;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;
import org.poifinder.dataMappers.DataMapper;
import org.poifinder.httpServer.auth.AuthUtilities;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class UserCreateMapper implements DataMapper{
    @NotNull
    private String username;

    @NotNull
    @Email(message = "Fornire un indirizzo email valido")
    private String email;

    @NotNull
    private String password;

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
        this.password = password;
    }

}