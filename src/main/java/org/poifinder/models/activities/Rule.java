package org.poifinder.models.activities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.poifinder.models.taxonomy.Taxonomy;

/**
 * Questa classe rappresenta una regola, nello specifico per i contest-
 * Tiene traccia della linea guida da seguire e di "punti" bonus o penalità
 * in caso di trasgressione o ottemperanza.
 */
@Entity
@Table(name="rules")
public class Rule extends Taxonomy {

    @Id
    private Long id;

    private String description;

    private Integer penalty;

    private Integer bonus;

    public Rule(){}

    public Rule(Long id, String description, Integer penalty, Integer bonus) {
        this.id = id;
        this.description = description;
        this.penalty = penalty;
        this.bonus = bonus;
    }

    /** getters **/

    public String getDescription() { return description; }

    public Integer getPenalty() { return penalty; }

    public Integer getBonus() { return bonus; }

    /** setters **/

    public void setId(Long id) { this.id = id; }

    public void setDescription(String description) { this.description = description; }

    public void setPenalty(Integer penalty) { this.penalty = penalty; }

    public void setBonus(Integer bonus) { this.bonus = bonus; }
}
