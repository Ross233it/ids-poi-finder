package org.models.taxonomy;

import java.util.ArrayList;

public class Category extends Taxonomy{

    private ArrayList<Category> subCategories;

    private Category parentCategory;

    public Category(String name, String description) {
        super(name, description);
        this.subCategories = new ArrayList<Category>();
    }

    /** getters **/
    @Override
    public Object[] getData() {
        return new Object[]{
                this.getId(),
                this.getName(),
                this.getDescription()};
    }

    public ArrayList<Category> getSubCategories() { return subCategories; }

    public Category getParentCategory() { return parentCategory; }

    /** setters **/
    public void addSubCategories(Category subCategory) { this.subCategories.add(subCategory); }

    public void removeSubCategories(Category subCategory) { this.subCategories.remove(subCategory); }

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

        sb.append("\"subCategories\": [");
        for (int i = 0; i < subCategories.size(); i++) {
            sb.append(subCategories.get(i).toString());
            if (i < subCategories.size() - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        sb.append("}");
        return sb.toString();
    }
}
