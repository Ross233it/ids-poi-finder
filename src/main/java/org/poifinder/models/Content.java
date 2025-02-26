package org.poifinder.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;
import org.poifinder.dataMappers.Views;
import org.poifinder.models.users.RegisteredUser;

import jakarta.persistence.*;

/**
 * Astrae il concetto di contenuto, inteso come dato o informazione
 * consultabile all'interno del sistema inserito da un autore ed approvato da un responsabile.
 */

@Getter
@Setter
@MappedSuperclass
public abstract  class Content implements IModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(Views.Public.class)
    private Long id;

    @JsonView(Views.Public.class)
    private String status = "pending";

    @ManyToOne
    @JsonView(Views.Internal.class)
    @JoinColumn(name = "author_id", nullable = true)
    private RegisteredUser author;

    @ManyToOne
    @JsonView(Views.Internal.class)
    @JoinColumn(name = "approver_id", nullable = true)
    private RegisteredUser approver;

    /** GETTERS **/

    @Override
    public Long getId() { return id; }


    /** SETTERS **/

    @Override
    public void setId(Long id) { this.id = id; }


}
