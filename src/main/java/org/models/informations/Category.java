package org.models.informations;

import java.util.ArrayList;

public class Category {

    private String name;

    private String description;

    private ArrayList<Category> subCategories;

    public Category(String name, String description) {
        this.name = name;
        this.description = description;
        this.subCategories = new ArrayList<Category>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<Category> getSubCategories() {
        return subCategories;
    }


}
