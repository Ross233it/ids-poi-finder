package org.poifinder.repositories.activities;

import org.poifinder.models.activities.Prize;
import org.poifinder.models.users.RegisteredUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrizeRepository extends JpaRepository<Prize, Long> {


    /**
     * Ritorna tutti i premi di un contenst presenti nello strato di persistenza
     * @param contestId l'id del contest
     * @return la lista dei premi correlati allo strato di persistenza
     */
    @Query(value = """
        SELECT p.* FROM prizes p
        JOIN contests_prizes cp ON p.id = cp.prize_id
        WHERE cp.contest_id = :contestId
        """, nativeQuery = true)
    List<Prize> getPrizesByContestId(@Param("contestId") Long contestId);

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
