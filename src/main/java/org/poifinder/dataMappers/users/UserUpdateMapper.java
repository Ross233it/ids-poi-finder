package org.poifinder.dataMappers.users;


import lombok.Getter;
import lombok.Setter;
import org.poifinder.dataMappers.DataMapper;
import org.poifinder.models.users.RegisteredUser;

/**
 * Classa che mappa gli oggetti di una richiesta di modifica
 * utente.
 */
@Setter
@Getter
public class UserUpdateMapper extends UserCreateMapper  implements DataMapper{

    Long id;

    public UserUpdateMapper(Long id,
                            String username,
                            String email,
                            String password,
                            String role,
                            Long municipality_id) {
       super(username, email, password, role, municipality_id);
       this.id = id;
    }


}