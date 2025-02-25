package org.poifinder.models.activities;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.poifinder.dataMappers.Views;
import org.poifinder.models.taxonomy.Taxonomy;

/**
 * Questa classe rappresenta una regola, nello specifico per i contest-
 * Tiene traccia della linea guida da seguire e di "punti" bonus o penalità
 * in caso di trasgressione o ottemperanza.
 */
@Getter
@Setter
@Entity
@Table(name="rules")
public class Rule extends Taxonomy {

    @Id
    private Long id;

    @JsonView(Views.Public.class)
    private String description;

    @JsonView(Views.Public.class)
    private Integer penalty;

    @JsonView(Views.Public.class)
    private Integer bonus;

    public Rule(){}

    public Rule(Long id, String description, Integer penalty, Integer bonus) {
        this.id = id;
        this.description = description;
        this.penalty = penalty;
        this.bonus = bonus;
    }
}
