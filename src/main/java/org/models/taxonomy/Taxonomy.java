package org.models.taxonomy;

import org.models.Content;

public abstract  class Taxonomy extends Content {

    String name;

    String description;

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
