package org.poifinder.services;

import org.poifinder.models.Content;

import java.util.List;
import java.util.Map;

public interface IService<D extends Content>{

    List<D> index();

    List<D> filter(Map<String, String> queryParams) throws Exception;

    D setStatus(Map<String, Object> data) throws Exception;

    D getObjectById(long id) throws Exception;

    D create(Map<String, Object> data) throws Exception;

    D update(long id, Map<String, Object> data) throws Exception;

    D delete(long id) throws Exception;
}
