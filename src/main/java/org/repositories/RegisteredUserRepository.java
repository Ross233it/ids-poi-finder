package org.repositories;

import org.models.users.User;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class RegisteredUserRepository extends Repository<User> implements IRepository<User> {


    public RegisteredUserRepository(String tableName) {
        super(tableName);
    }


    public User create(User user) throws Exception {
        if (user == null) {
            throw new IllegalArgumentException("L'entity non può essere null.");
        }
        List<String> columns = Arrays.asList("username", "email", "password", "role");
        Object[] data = user.getData();
        super.save(columns,data);
        return user;
    }

    @Override
    public int delete(int id) throws SQLException {
        return 0;
    }
}
