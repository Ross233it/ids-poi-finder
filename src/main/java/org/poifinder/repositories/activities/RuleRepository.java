package org.poifinder.repositories.activities;

import org.poifinder.models.activities.Rule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RuleRepository extends JpaRepository<Rule, Long> {

    /**
     * Ritorna tutte le regole di un contest presenti nello
     * strato di persistenza
     * @param contestId l'id del contest
     * @return la lista delle regole correlate al contest
     */
    @Query(value = """
        SELECT r.* FROM rules r
        JOIN contests_rules cr ON r.id = cr.rule_id
        WHERE cr.contest_id = :contestId
        """, nativeQuery = true)
    List<Rule> getRulesByContestId(@Param("contestId") Long contestId);



}
