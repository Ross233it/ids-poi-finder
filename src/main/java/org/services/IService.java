package org.services;

import org.models.Model;

import java.util.Map;
public interface IService<D extends Model>{

    String index() ;

    D create(Map<String, Object> data) throws Exception;

    D getObjectById(long id) throws Exception;

    D delete(long id) throws Exception;
}
