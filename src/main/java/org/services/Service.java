package org.services;

import org.repositories.Repository;

import java.util.Map;
public interface Service<D>{

    D create(Map<String, Object> data);

    Repository repository = null;

//    public D create(Map<String, Object> objectData) throws SQLException;
}
