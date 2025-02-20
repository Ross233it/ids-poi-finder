package org.poifinder.models.taxonomy;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * Questa classe rappresenta un tag - etichetta qualificativa
 * associabile ad un oggetto
 */
@Entity
@Table(name="tags")
public class Tag extends Taxonomy {

    public Tag(String name, String description) {
        super(name, description);
    }

    public Tag() { super(); }

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
