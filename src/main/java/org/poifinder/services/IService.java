package org.poifinder.services;

import org.poifinder.dataMappers.DataMapper;

import org.poifinder.models.IModel;

import java.util.List;
import java.util.Map;

public interface IService<D extends IModel>{

    List<D> index();

    List<D> filter(Map<String, String> queryParams) throws Exception;

    D setStatus(Map<String, Object> data) throws Exception;

    D getObjectById(long id) throws Exception;

    DataMapper<D> create(DataMapper<D> entityData) throws Exception;

    DataMapper<D> update(long id, DataMapper<D> entityData) throws Exception;

    D delete(long id) throws Exception;
}
