package org.services;

import org.models.Model;

import java.util.Map;
public interface IService<D extends Model>{

    String index() ;

    D create(Map<String, Object> data) throws Exception;

    D getObjectById(int id) throws Exception;

    D delete(int id) throws Exception;
}
