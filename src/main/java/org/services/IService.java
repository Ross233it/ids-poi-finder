package org.services;

import org.repositories.IRepository;

import java.util.Map;
public interface IService<D>{

    String index() ;

    D create(Map<String, Object> data);

    String getById(String id);
}
