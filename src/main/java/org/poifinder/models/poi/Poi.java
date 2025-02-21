package org.poifinder.models.poi;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import org.poifinder.dataMappers.Views;
import org.poifinder.models.Content;
import org.poifinder.models.municipalities.Municipality;
import org.poifinder.models.taxonomy.Category;
import org.poifinder.models.GeoLocation;
import org.poifinder.models.taxonomy.Tag;
import java.util.List;

@Entity
@Table(name="pois")
public class Poi extends Content implements IPoi {

    @JsonView(Views.Public.class)
    private String name;

    @JsonView(Views.Public.class)
    private String  description;

    @JsonView(Views.Public.class)
    private String  type;

    @JsonView(Views.Public.class)
    @Column(name = "is_logical")
    private Boolean isLogical = false;

    @ManyToOne
    @JoinColumn(name="municipality_id", referencedColumnName = "id", nullable = false, unique = false)
    @JsonView(Views.Public.class)
    private Municipality municipality = null;

    @OneToOne
    @JoinColumn(name="geolocation_id", referencedColumnName = "id", nullable = false, unique = false)
    @JsonView(Views.Public.class)
    private GeoLocation geoLocation = null;

    @ManyToMany
    @JoinTable(name= "pois_categories",
            joinColumns = @JoinColumn(name = "poi_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    @JsonView(Views.Public.class)
    private List<Category> categories;

    @ManyToMany
    @JoinTable(name= "pois_itineraries",
            joinColumns = @JoinColumn(name = "poi_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    @JsonView(Views.Public.class)
    private List<Tag> tags;


    public Poi(PoiBuilder builder) {
        this.name =     builder.getPoiName();
        this.description = builder.getDescription();
        this.isLogical =   builder.getIsLogical();
        this.municipality= builder.getMunicipality();
        this.geoLocation = builder.getGeoLocation();
        this.tags =        builder.getTags();
        this.categories =  builder.getCategories();
        this.type =        builder.getType();
        this.setStatus(builder.getStatus());
        this.setApprover(builder.getApprover());
        this.setAuthor(builder.getAuthor());
    }

    public Poi() { }

    @Override
    public String toString() {
        String resultString = "{"
                + "\"id\":\"" + getId() + "\","
                + "\"poiname\":\"" + name + "\","
                + "\"description\":\"" + description + "\","
                + "\"type\":\"" + type + "\","
                + "\"is_logical\":\"" + isLogical + "\","
                + "\"status\":\"" + getStatus() + "\""
                ;
        if(municipality != null)
            resultString += ",\"municipality\": "+ municipality.toString();
        if(geoLocation != null)
            resultString += ",\"geoLocation\": "+ geoLocation.toString();
        if(getAuthor() != null)
            resultString += ",\"author\": " + getAuthor().toString();
    return resultString +     "}";
    }

    /** getters **/

    @Override
    public Municipality getMunicipality() { return municipality; }

    @Override
    public GeoLocation getGeoLocation() { return geoLocation; }

    public String  getName() { return name; }

    public Boolean isLogical(){ return this.isLogical;}

    public String  getDescription() { return description; }

    public String  getType() { return type; }

    /** setters **/

    @Override
    public void setGeoLocation(GeoLocation geoLocation) {
        this.geoLocation = geoLocation;
    }

    @Override
    public void setMunicipality(Municipality municipality) {
        this.municipality = municipality;
    }

    public void setName(String poiname) {
        this.name = poiname;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setLogical(Boolean logical) {
        isLogical = logical;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }
}
