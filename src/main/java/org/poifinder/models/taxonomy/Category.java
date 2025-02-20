package org.poifinder.models.taxonomy;

import jakarta.persistence.*;

import java.util.ArrayList;

@Entity
@Table(name="categories")
public class Category extends Taxonomy{


    @ManyToOne
    @JoinColumn(name = "parent_id", nullable = true)
    private Category parentCategory;

    public Category(String name, String description) {
        super(name, description);
    }

    public Category() { super(); }

    /** getters **/

    public Category getParentCategory() { return parentCategory; }

    /** setters **/

    public void setParentCategory(Category parentCategory) { this.parentCategory = parentCategory; }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");

        sb.append("\"id\": ").append(this.getId()).append(", ");
        sb.append("\"name\": \"").append(this.getName()).append("\", ");
        sb.append("\"description\": \"").append(this.getDescription()).append("\", ");

        if (parentCategory != null) {
            sb.append("\"parentCategory\": ").append(parentCategory.toString()).append(", ");
        } else {
            sb.append("\"parentCategory\": null, ");
        }
        sb.append("}");
        return sb.toString();
    }
}
