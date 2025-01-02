package org.services;

import java.util.Map;
public interface IService<D>{

    String index() ;

    D create(Map<String, Object> data);

    D getObjectById(int id) throws Exception;

    D delete(int id) throws Exception;
}
