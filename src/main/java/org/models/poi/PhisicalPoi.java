package org.models.poi;

import org.models.Municipality;
import org.models.informations.Category;
import org.models.informations.GeoLocation;
import org.models.informations.Tag;


import java.util.ArrayList;

public class PhisicalPoi implements Poi {
    private long id;

    private String name;

    private String description;

    private Boolean isLogical = false;

    private Municipality municipality;

    private GeoLocation geoLocation;

    private ArrayList<Tag> tags;

    private ArrayList<Category> categories;


    //todo remove test constructor
    public PhisicalPoi() {
        this.name= "Test POI";
        this.description= "Test description";
        this.isLogical = false;
        this.municipality = null;
        this.geoLocation = null;
        this.tags = new ArrayList<Tag>();
        this.categories = new ArrayList<Category>();
    }

    public PhisicalPoi(String name,
                       String description,
                       boolean isLogical,
                       Municipality municipality,
                       GeoLocation geoLocation){
        this.name = name;
        this.isLogical  = isLogical;
        this.municipality = municipality;
        this.geoLocation  = geoLocation;
        this.tags = new ArrayList<Tag>();
        this.categories = new ArrayList<Category>();
    }
    //todo remove
    public PhisicalPoi(String name,
                       String description,
                       boolean isLogical,
                       int municipality_id,
                       GeoLocation geoLocation){
        this.name = name;
        this.isLogical  = isLogical;

        this.geoLocation  = geoLocation;
        this.tags = new ArrayList<Tag>();
        this.categories = new ArrayList<Category>();
    }

    public Boolean isLogical() {
        return this.isLogical;
    }

    public void setLogical(Boolean logical) {
        isLogical = logical;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Municipality getMunicipality() {
        return municipality;
    }

    public void setMunicipality(Municipality municipality) {
        this.municipality = municipality;
    }

    public GeoLocation getGeoLocation() {
        return geoLocation;
    }

    public void setGeoLocation(GeoLocation geoLocation) {
        this.geoLocation = geoLocation;
    }

    public ArrayList<Tag> getTags() {
        return tags;
    }

    public void addTag(Tag tag) {
        this.tags.add(tag);
    }

    public void removeTag(Tag tag) {
        this.tags.remove(tag);
    }

    public ArrayList<Category> getCategories() {
        return categories;
    }

    public void addCategory(Category category) {
        this.categories.add(category);
    }

    public void removeCategory(Category category) {
        this.categories.remove(category);
    }

}
