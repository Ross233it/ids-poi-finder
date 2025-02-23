package org.poifinder.repositories.activities;

import org.poifinder.models.activities.Activity;
import org.poifinder.models.users.RegisteredUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {


    /**
     * Query di ricerca Attività in base all'autore
     * @return List<Activity> lista di punti di interesse ritrovata
     */
    List<Activity>findByAuthor(RegisteredUser author);

    /**
     * Query di ricerca Comuni in base all'utente che lo ha pubblicato
     * @return List<Activity> lista di punti di interesse ritrovata
     */
    List<Activity> findByApprover(RegisteredUser approver);



}
