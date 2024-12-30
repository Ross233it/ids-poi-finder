package org.services;

import org.repositories.Repository;

import java.util.Map;

/**
 * Ha la responsabilità di gestire la logica di business connessa alla
 * manipolazione ed all'interazione con gli oggetti di tipo POI.
 */

public class Service<D> implements IService<D> {

    Repository repository;

    public Service(Repository repository) {
        this.repository = repository;
    }

    public String index() {
        try {
            return this.repository.index();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getById(String id) {
       return "";
    }

    /**
     * Crea un nuovo poi partendo da una serie di dati già validati
     * @param objectData
     * @return
     */
    public D create(Map<String, Object> objectData){
      return null;
    }

}
