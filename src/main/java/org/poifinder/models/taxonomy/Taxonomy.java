package org.poifinder.models.taxonomy;

import jakarta.persistence.MappedSuperclass;
import org.poifinder.models.Content;

@MappedSuperclass
public abstract  class Taxonomy extends Content {

    String name;

    String description;

    public Taxonomy() {}

    public Taxonomy(String name, String description) {
        this.name = name;
        this.description = description;
    }


    /** getters **/

    public String getName() { return name; }

    public String getDescription() { return description;}


    /** setters **/

    public void setName(String name) { this.name = name; }

    public void setDescription(String description) {this.description = description;}
}
