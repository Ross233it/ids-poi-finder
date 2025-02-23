package org.poifinder.repositories.activities;

import org.poifinder.models.activities.Prize;
import org.poifinder.models.users.RegisteredUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrizeRepository extends JpaRepository<Prize, Long> {


    /**
     * Query di ricerca Premi in base all'autore
     * @return List<Prize> lista di punti di interesse ritrovata
     */
    List<Prize>findByAuthor(RegisteredUser author);

    /**
     * Query di ricerca Comuni in base all'utente che lo ha pubblicato
     * @return List<Prize> lista di punti di interesse ritrovata
     */
    List<Prize> findByApprover(RegisteredUser approver);



}
