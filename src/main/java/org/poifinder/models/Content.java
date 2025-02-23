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
@JsonIgnoreProperties({ "author" , "approver" })
public abstract  class Content implements IModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(Views.Public.class)
    private Long id;


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

//    public String getStatus() { return status; }
//
//    public RegisteredUser getAuthor() { return author; }
//
//    public RegisteredUser getApprover() { return approver; }


    /** SETTERS **/

    @Override
    public void setId(Long id) { this.id = id; }

//    public void setStatus(String status) { this.status = status; }
//
//    public void setAuthor(RegisteredUser author) { this.author = author; }
//
//    public void setApprover(RegisteredUser approver) { this.approver = approver; }


    /**
     * Restituisce una rappresentazione testuale dell'oggetto
     * @return
     */
    @Override
    public String toString(){
        return "";
//        try {
//            return DataMapper.mapObjectToJson(this);
//        } catch (IllegalAccessException e) {
//            throw new RuntimeException(e);
//        }
    };
}
