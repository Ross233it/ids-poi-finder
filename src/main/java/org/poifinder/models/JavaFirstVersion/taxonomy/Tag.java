package org.poifinder.models.JavaFirstVersion.taxonomy;

import jakarta.persistence.Entity;

/**
 * Questa classe rappresenta un tag - etichetta qualificativa
 * associabile ad un oggetto
 */
@Entity
public class Tag extends Taxonomy {

    public Tag(String name, String description) {
        super(name, description);
    }

    public Tag() {
        super();
    }

    /**
     * Restituisce un array di oggetti che rappresentano i dati dell'oggetto corrente.
     * @return Array di oggetti.
     */

    @Override
    public Object[] getData() {
        return new Object[]{
                this.getId(),
                this.getName(),
                this.getDescription()};
    }

    /**
     * Restituisce una stringa che rappresenta l'oggetto corrente.
     * @return String dell'oggetto corrente.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");

        sb.append("\"id\": ").append(this.getId()).append(", ");
        sb.append("\"name\": \"").append(this.getName()).append("\", ");
        sb.append("\"description\": \"").append(this.getDescription()).append("\"");

        sb.append("}");
        return sb.toString();
    }
}
