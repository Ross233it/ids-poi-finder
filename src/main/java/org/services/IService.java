package org.services;

import org.models.Content;
import org.models.users.RegisteredUser;

import java.util.List;
import java.util.Map;

public interface IService<D extends Content>{

    List<D> index();

    List<D> search(String queryStringSearchTerm) throws Exception;

    D getObjectById(long id) throws Exception;

    D create(Map<String, Object> data) throws Exception;

    D update(long id, Map<String, Object> data) throws Exception;

    D delete(long id) throws Exception;
}
