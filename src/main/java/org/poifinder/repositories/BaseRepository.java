package org.poifinder.repositories;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.transaction.annotation.Transactional;

import org.poifinder.models.Content;

import java.io.IOException;

import java.util.*;


/**
 * Classe astratta che implementa i metodi comuni a tutti i repository
 * @param <D> il tipo di oggetto che il repository gestisce
 */
public abstract class BaseRepository<D>  implements IRepository<D> {

    @PersistenceContext
    private EntityManager entityManager;


    /**
     * Crea un nuovo elemento nella tabella corrente
     * @param entity l'oggetto da inserire nello strato di persistenza
     * @return D entity l'oggetto inserito null altrimenti
     * @throws Exception
     */
//    @Override
//    @Transactional
//    public D create(D entity) throws Exception {
//        if (entity == null) {
//            throw new IllegalArgumentException("L'entity non può essere null.");
//        }
//        return save(entity);
//    }





    /**
     * Ricerca un elemento in base ad una query ed un parametro di ricerca
     * @return
     * @throws Exception
     */
    @Override
    public List<D> search(Map<String, String> queryStringParams) throws Exception{
        return null;
    }



    /**
     * Modifica lo stato di un punto di interesse
     * @param entity D l'oggetto  di cui modificare lo stato
     * @return poi il poi modificato
     * @throws Exception
     */
    public D setStatus(D entity) throws Exception {
//        if (entity == null) {
//            throw new IllegalArgumentException("L'entity non può essere null.");
//        }
//        long entityId = entity.getId();
//        long approverId = entity.getApprover().getId();
//        String status = entity.getStatus();
//
//        Object[] data = { status, approverId, entityId };
//
//        String query = "UPDATE " + this.tableName + " " +
//                "SET status = ?, " +
//                "approver_id = ? " +
//                "WHERE id = ?;";
//        long modifiedEntityId = DbUtilities.executeQuery(query, data);
//        return entity;
        return null;
    };
}

