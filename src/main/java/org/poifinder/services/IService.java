package org.poifinder.services;

import org.poifinder.dataMappers.DataMapper;

import org.poifinder.models.IModel;

import java.util.List;
import java.util.Map;

/**
 * Contratto che definisce alcune funzionalità da implementara per le classi
 * che erogano servizi nell'ambito della logica di business del sistema.
 * @param <D>
 */
public interface IService<D extends IModel>{

    List<D> index();

    List<D> filter(Map<String, String> queryParams) throws Exception;

    D setStatus(Long id, String status) throws Exception;

    D getObjectById(Long id) throws Exception;

    D create(D entity) throws Exception;

    D update(Long id, DataMapper entityData) throws Exception;

    D delete(Long id) throws Exception;
}
