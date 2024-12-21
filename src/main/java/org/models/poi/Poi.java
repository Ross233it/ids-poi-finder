package org.models.poi;

import org.models.Municipality;
import org.models.informations.Category;
import org.models.informations.GeoLocation;
import org.models.informations.Tag;
import java.util.List;

public class Poi implements IPoi {
    private long id;

    private String name;

    private String description;

    private Boolean isLogical = false;

    private Municipality municipality = null;

    private GeoLocation geoLocation = null;

    private List<Tag> tags;

    private List<Category> categories;

    public Poi(PoiBuilder builder) {
        this.name =        builder.getName();
        this.description = builder.getDescription();
        this.isLogical =   builder.getIsLogical();
        this.municipality= builder.getMunicipality();
        this.geoLocation = builder.getGeoLocation();
        this.tags =        builder.getTags();
        this.categories =  builder.getCategories();
    }

    /** getters **/
    @Override
    public Municipality getMunicipality() {
        return municipality;
    }

    @Override
    public void setMunicipality(Municipality municipality) {this.municipality = municipality;}

    @Override
    public GeoLocation getGeoLocation() {
        return geoLocation;
    }

    @Override
    public void setGeoLocation(GeoLocation geoLocation) {
        this.geoLocation = geoLocation;
    }

    public long    getId()         { return id; }
    public String  getName()       { return name; }
    public String  getDescription(){ return description; }
    public Boolean getIsLogical()  { return isLogical; }
    public Object[]  getData(){ return new Object[] { this.name, this.description, this.isLogical };}

    /** tags **/
    public List<Tag> getTags() {
        return this.tags;
    }

    public void addTag(Tag tag) {
        this.tags.add(tag);
    }

    public void removeTag(Tag tag) {
        this.tags.remove(tag);
    }

    /** categories **/
    public List<Category> getCategories() {
        return this.categories;
    }

    public void addCategory(Category category) {
        this.categories.add(category);
    }

    public void removeCategory(Category category) {
        this.categories.remove(category);
    }
}
