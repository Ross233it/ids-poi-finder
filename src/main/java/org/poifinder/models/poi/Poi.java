package org.poifinder.models.poi;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.poifinder.dataMappers.Views;
import org.poifinder.models.Content;
import org.poifinder.models.municipalities.Municipality;
import org.poifinder.models.taxonomy.Category;
import org.poifinder.models.GeoLocation;
import org.poifinder.models.taxonomy.Tag;
import java.util.List;

/**
 * Questa classe rappresenta un punto di interesse (POI) con
 * attributi e relazioni.
 */
@Setter
@Getter
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
    @JsonBackReference
    private Municipality municipality = null;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="geolocation_id", referencedColumnName = "id",  unique = false)
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

    @Transient
    private Long municipality_id;

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


    /** setters **/

    @Override
    public void setGeoLocation(GeoLocation geoLocation) {
        this.geoLocation = geoLocation;
    }

    @Override
    public void setMunicipality(Municipality municipality) {
        this.municipality = municipality;
    }

    public void setLogical(Boolean logical) {
        isLogical = logical;
    }

}
