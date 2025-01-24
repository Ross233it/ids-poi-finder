package org.models.activities;

import org.models.Content;
import org.models.users.RegisteredUser;

/**
 * Questa classe astrae il concetto di attività intesa come contenuto volto a
 * promuovere l'interazione tra utenti o la partecipazione ad eventi.
 */
public class Activity extends Content {
    private String name;
    private String description;
    private String status;
    private String type;

    private RegisteredUser author;
    private RegisteredUser approver;


    public Activity(String name, String description, String type) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.approver = null;
        this.author = null;
        this.status = "pending";
    }

    /** setters **/

    public void setName(String name) { this.name = name; }

    public void setDescription(String description) {this.description = description; }

    public void setStatus(String status) { this.status = status; }

    public void setAuthor(RegisteredUser author) { this.author = author;}

    public void setValidator(RegisteredUser approver) { this.approver = approver; }

    /** getters **/

    /**
     * Restituisce i dati dell'oggetto
     * @return Object[] array di oggetti
     */
    @Override
    public Object[] getData() {
        return new Object[] {
            this.getName(),
            this.getDescription(),
            this.getType(),
            this.getStatus(),
            this.getAuthor().getId(),
            this.getValidator()
        };
    }

    @Override
    public String toString() {
        return "";
    }

    public String getName() { return name;}

    public String getDescription() { return description; }

    public String getStatus() { return status; }

    public RegisteredUser getAuthor() { return author; }

    public RegisteredUser getValidator() { return approver; }

    public String getType() { return type; }

}
