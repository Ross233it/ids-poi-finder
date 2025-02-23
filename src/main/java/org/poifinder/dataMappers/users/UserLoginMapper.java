package org.poifinder.dataMappers.users;

import lombok.Getter;
import lombok.Setter;
import org.poifinder.dataMappers.DataMapper;


@Getter
@Setter
public class UserLoginMapper implements DataMapper{


    private String username;

    private String password;


    public UserLoginMapper(String username,
                           String password){
        this.username = username;
        this.password = password;
    }
}
