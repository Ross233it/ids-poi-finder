package org.poifinder.dataMappers.users;

import org.antlr.v4.runtime.misc.NotNull;
import org.poifinder.dataMappers.DataMapper;
import org.poifinder.models.users.RegisteredUser;
import org.springframework.stereotype.Component;


public class UserLoginMapper extends DataMapper<RegisteredUser> {


    private String username;


    private String password;


    public UserLoginMapper(String username,
                           String password){
        this.username = username;
        this.password = password;
    }

    public String getUsername() { return username; }

    public String getPassword() { return password; }


}
