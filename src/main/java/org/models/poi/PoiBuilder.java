package org.models.poi;

import org.models.Builder;
import org.models.municipalities.Municipality;
import org.models.taxonomy.Category;
import org.models.GeoLocation;
import org.models.taxonomy.Tag;

import java.util.ArrayList;
import java.util.List;

public class  PoiBuilder extends Builder<Poi> {
    private String         poiname;
    private String         description;
    private boolean        isLogical;
    private String         type;
    private Municipality   municipality;
    private GeoLocation    geoLocation;
    private List<Tag>      tags = new ArrayList<>();
    private List<Category> categories = new ArrayList<>();

    public PoiBuilder(String name, String description, boolean isLogical) {
        this.poiname = name;
        this.description = description;
        this.isLogical = isLogical;
    }

    public PoiBuilder municipality(Municipality municipality) {
        this.municipality = municipality;
        return this;
    }

    public PoiBuilder geoLocation(GeoLocation geoLocation) {
        this.geoLocation = geoLocation;
        return this;
    }

    public PoiBuilder type(String type) {
        this.type = type;
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

    @Override
    public Poi build(){
        return new Poi(this);
    }


    /*** getters ***/
    public String         getPoiName()     { return poiname;}
    public String         getDescription() { return description; }
    public String         getType()        { return type; }
    public boolean        getIsLogical()   { return isLogical; }
    public Municipality   getMunicipality(){ return municipality; }
    public GeoLocation    getGeoLocation() { return geoLocation; }
    public List<Tag>      getTags()        { return tags; }
    public List<Category> getCategories()  { return categories; }
}

