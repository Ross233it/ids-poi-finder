package org.poifinder.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.poifinder.dataMappers.DataMapper;
import org.poifinder.models.users.RegisteredUser;

import jakarta.persistence.*;

/**
 * Astrae il concetto di contenuto, inteso come dato o informazione
 * consultabile all'interno del sistema inserito da un autore ed approvato da un responsabile.
 */

@MappedSuperclass
@JsonIgnoreProperties({ "author" , "approver" })
public abstract  class Content implements IModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String status;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = true)
    private RegisteredUser author;

    @ManyToOne
    @JoinColumn(name = "approver_id", nullable = true)
    private RegisteredUser approver;

    /** GETTERS **/

    @Override
    public long getId() { return id; }

    public String getStatus() { return status; }

    public RegisteredUser getAuthor() { return author; }

    public RegisteredUser getApprover() { return approver; }


    /** SETTERS **/

    @Override
    public void setId(long id) { this.id = id; }

    public void setStatus(String status) { this.status = status; }

    public void setAuthor(RegisteredUser author) { this.author = author; }

    public void setApprover(RegisteredUser approver) { this.approver = approver; }


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
