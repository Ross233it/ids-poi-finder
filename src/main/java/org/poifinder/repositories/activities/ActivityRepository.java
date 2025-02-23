package org.poifinder.repositories.activities;

import org.poifinder.models.activities.Activity;
import org.poifinder.models.users.RegisteredUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {


    /**
     * Query di ricerca attività unitamente ai poi correlati
     * @param id l'id dell'attività
     * @return Activity l'attività completa di poi.
     */
//        @Query("SELECT a FROM Activity a " +
//                "JOIN FETCH a.poiList p " +
//                "JOIN activities_pois ap ON ap.activity_id = a.id AND ap.poi_id = p.id " +
//                "WHERE a.id = :id")
//        Activity findByIdWithPois(@Param("id") Long id);




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
