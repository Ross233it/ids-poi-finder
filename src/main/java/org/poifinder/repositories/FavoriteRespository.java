package org.poifinder.repositories;


import org.poifinder.models.users.FavoriteContents;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface FavoriteRespository extends IRepository<FavoriteContents> {


    /**
     * Ricerca tutte le liste di preferite associate ad un utente
     */
    List<FavoriteContents>getByUserId(Long userId);

    /**
     * Ricerca la lista di preferiti in base all'id dell'utente
     * @param userId l'id dell'utente
     * @return List<FavoriteContents> i preferiti dell'utente
     */
    FavoriteContents findByUserId(Long userId);
}
