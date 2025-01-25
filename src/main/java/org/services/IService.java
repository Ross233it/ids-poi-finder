package org.services;

import org.models.Content;
import org.models.users.RegisteredUser;

import java.util.Map;

public interface IService<D extends Content>{

    String index() ;

    D create(Map<String, Object> data, RegisteredUser author) throws Exception;

    D update(long id, Map<String, Object> data) throws Exception;

    D getObjectById(long id) throws Exception;

    D delete(long id) throws Exception;

    D search(String queryStringSearchTerm) throws Exception;
}
