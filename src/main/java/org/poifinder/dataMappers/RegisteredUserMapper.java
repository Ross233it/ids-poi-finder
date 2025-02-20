package org.poifinder.dataMappers;

import org.poifinder.models.municipalities.Municipality;
import org.poifinder.models.users.RegisteredUser;
import org.poifinder.services.MunicipalityService;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service
public class RegisteredUserMapper extends DataMapper<RegisteredUser> {

    public RegisteredUser mapDataToObject(Map<String, Object> result) {
        if(result.containsKey("author") && result.get("author") != null){
            return (RegisteredUser) result.get("author");
        }
        if (result.get("method") == "insert") {
            return this.mapRequestDataToObject(result);
        }
        RegisteredUser user = buildBaseUser(result);

        if(result.get("municipality_id") != null){
            MunicipalityService service = new MunicipalityService();
            Municipality municipality = null;
            try {
                municipality = service.getObjectById((Long) result.get("municipality_id"));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            user.setMunicipality(municipality);
        }

        if(result.containsKey("A_id") ){
            Integer id = (Integer) result.getOrDefault("A_id", 0);
            user.setId(id.longValue());
        }
        return user;
    }

    public RegisteredUser mapRequestDataToObject(Map<String, Object> result){
            RegisteredUser user = buildBaseUser(result);
            user.setPassword((String) result.get("password"));
            user.setSalt((String) result.get("salt"));
            return user;
    }

    private RegisteredUser buildBaseUser(Map<String, Object> result){
        RegisteredUser user = new RegisteredUser(
                (String) result.getOrDefault("username", null),
                (String) result.getOrDefault("email", null),
                (String) result.getOrDefault("role", null)
        );
        return user;
    }

    @Override
    public RegisteredUser updateEntityFromMap(RegisteredUser item, Map<String, Object> result) {
        return null;
    }
}