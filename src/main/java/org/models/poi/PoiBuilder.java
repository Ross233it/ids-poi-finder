package org.models.poi;

import org.models.Municipality;
import org.models.taxonomy.Category;
import org.models.GeoLocation;
import org.models.taxonomy.Tag;

import java.util.ArrayList;
import java.util.List;

public class  PoiBuilder {
    private String         name;
    private String         description;
    private boolean        isLogical;
    private Municipality   municipality;
    private GeoLocation    geoLocation;
    private List<Tag>      tags = new ArrayList<>();
    private List<Category> categories = new ArrayList<>();
    private String         status;

    public PoiBuilder(String name, String description, boolean isLogical) {
        this.name = name;
        this.description = description;
        this.isLogical = isLogical;
        this.status = "pending";
    }

    public PoiBuilder municipality(Municipality municipality) {
        this.municipality = municipality;
        return this;
    }

    public PoiBuilder geoLocation(GeoLocation geoLocation) {
        this.geoLocation = geoLocation;
        return this;
    }

    public PoiBuilder tags(List<Tag> tags) {
        this.tags = tags;
        return this;
    }

    public PoiBuilder categories(List<Category> categories) {
        this.categories = categories;
        return this;
    }

    public Poi build() {
        return new Poi(this);
    }

    /*** getters ***/

    public String        getName()        { return name;}
    public String        getStatus()      { return status;}
    public String        getDescription() { return description; }
    public boolean       getIsLogical()   { return isLogical; }
    public Municipality  getMunicipality(){ return municipality; }
    public GeoLocation   getGeoLocation() { return geoLocation; }
    public List<Tag>     getTags()        { return tags; }
    public List<Category>getCategories()  { return categories; }
}

