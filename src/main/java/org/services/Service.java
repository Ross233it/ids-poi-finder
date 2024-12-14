package org.services;

import org.repositories.Repository;

import java.sql.SQLException;
import java.util.Map;
public interface Service<D>{

    public Repository repository = null;

    public D create(Map<String, Object> objectData) throws SQLException;
}
