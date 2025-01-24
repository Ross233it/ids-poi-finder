package org.models.poi;

import org.models.Content;
import org.models.Municipality;
import org.models.taxonomy.Category;
import org.models.GeoLocation;
import org.models.taxonomy.Tag;
import java.util.List;

public class Poi extends  IPoi {
    private String  name;

    private String  description;

    private String  type;

    private Boolean isLogical = false;

    private Municipality municipality = null;

    private GeoLocation geoLocation = null;

    private List<Tag> tags;

    private List<Category> categories;

    private String status;

    public Poi(PoiBuilder builder) {
        this.name =        builder.getName();
        this.description = builder.getDescription();
        this.isLogical =   builder.getIsLogical();
        this.municipality= builder.getMunicipality();
        this.geoLocation = builder.getGeoLocation();
        this.tags =        builder.getTags();
        this.categories =  builder.getCategories();
        this.status =      builder.getStatus();
    }


    //todo implments toString
    @Override
    public String toString() {
        return "";
    }

    /** getters **/

    /**
     * Restituisce i dati dell'oggetto
     * @return Object[] array di oggetti
     */
    @Override
    public Object[] getData(){ return new Object[] {
            this.name,
            this.description,
            this.type,
            this.status,
            this.isLogical,
            this.municipality.getId(),
            this.geoLocation.getId()
        };
    }

    @Override
    public Municipality getMunicipality() {return municipality;}

    @Override
    public GeoLocation getGeoLocation() { return geoLocation; }

    public String  getName()       { return name; }

    /** setters **/
    @Override
    public void setGeoLocation(GeoLocation geoLocation) {
        this.geoLocation = geoLocation;
    }

    @Override
    public void setMunicipality(Municipality municipality) {this.municipality = municipality;}

    public void setStatus(String status) { this.status = status; }



    //todo remove and refactor this
//    /** tags **/
//    public List<Tag> getTags() {
//        return this.tags;
//    }
//
//    public void addTag(Tag tag) {
//        this.tags.add(tag);
//    }
//
//    public void removeTag(Tag tag) {
//        this.tags.remove(tag);
//    }
//
//    /** categories **/
//    public List<Category> getCategories() {
//        return this.categories;
//    }
//
//    public void addCategory(Category category) {
//        this.categories.add(category);
//    }
//
//    public void removeCategory(Category category) {
//        this.categories.remove(category);
//    }
}
