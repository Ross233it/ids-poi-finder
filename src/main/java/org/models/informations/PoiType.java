package org.models.informations;




public class PoiType implements Information{
    private boolean isPhisical;

    private String name;

    private String description;

    private String[] categories;

    public PoiType(boolean isPhisical,
                   String name,
                   String description,
                   String[] categories) {
        this.isPhisical = isPhisical;
        this.name = name;
        this.description = description;
        this.categories = categories;
    }

    public boolean isPhisical() {
        return isPhisical;
    }

    public void setPhisical(boolean phisical) {
        isPhisical = phisical;
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

    public String[] getCategories() {
        return categories;
    }

    public void setCategories(String[] categories) {
        this.categories = categories;
    }
}
