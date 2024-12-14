package org.models.informations;

public class Tag {
    String name;

    String description;

    public Tag(String name) {
        this.name = name;
        this.description = "";
    }

    public Tag(String name, String description) {
        this.name = name;
        this.description = description;
    }

    /**
     * Ritorna il tag vero e proprio
     * @return String name
     */
    public String getName() {
        return name;
    }

    /**
     * Setta il parametro name del tag
     * @param name String il nuovo nome del tag.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Ritorna la descrizione del tag
     * @return description String la descrizione del tag
     */
    public String getDescription() {
        return description;
    }

    /**
     * Aggiorna la descrizione del tag
     * @param description String nuova descrizione da assegnare
     */
    public void setDescription(String description) {
        this.description = description;
    }
}
