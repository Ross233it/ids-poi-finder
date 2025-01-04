package org.models.activities;

import org.models.Content;
import org.models.poi.IPoi;
import org.models.poi.Poi;
import org.models.users.RegisteredUser;

/**
 * Questa classe astrae il concetto di attività intesa come contenuto volto a
 * promuovere l'interazione tra utenti o la partecipazione ad eventi.
 */
public abstract class Activity extends Content {
    private String name;
    private String description;
    private String status;

    private RegisteredUser author;
    private RegisteredUser validator;


    public Activity(String name, String description, RegisteredUser author) {
        this.name = name;
        this.description = description;
        this.author = author;
        this.validator = null;
        this.status = "pending";
    }

    /** setters **/

    public void setName(String name) { this.name = name; }

    public void setDescription(String description) {this.description = description; }

    public void setStatus(String status) { this.status = status; }

    public void setAuthor(RegisteredUser author) { this.author = author;}

    public void setValidator(RegisteredUser validator) { this.validator = validator; }

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
            this.getStatus(),
            this.getAuthor(),
            this.getValidator()
        };
    }

    public String getName() { return name;}

    public String getDescription() { return description; }

    public String getStatus() { return status; }

    public RegisteredUser getAuthor() { return author; }

    public RegisteredUser getValidator() { return validator; }

    public abstract void addPoi(IPoi poi);

    public abstract void removePoi(IPoi poi);
}
