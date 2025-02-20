package org.poifinder.models.poi;

import jakarta.persistence.*;
import org.poifinder.models.Content;
import org.poifinder.models.municipalities.Municipality;
import org.poifinder.models.taxonomy.Category;
import org.poifinder.models.GeoLocation;
import org.poifinder.models.taxonomy.Tag;
import java.util.List;

@Entity
@Table(name="pois")
public class Poi extends Content implements IPoi {

    private String  poiname;

    private String  description;

    private String  type;

    @Column(name = "is_logical")
    private Boolean isLogical = false;

    @OneToOne
    @JoinColumn(name="municipality_id", referencedColumnName = "id", nullable = false)
    private Municipality municipality = null;

    @OneToOne
    @JoinColumn(name="geolocation_id", referencedColumnName = "id", nullable = false)
    private GeoLocation geoLocation = null;

    @ManyToMany
    @JoinTable(name= "pois_categories",
            joinColumns = @JoinColumn(name = "poi_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private List<Category> categories;

    @ManyToMany
    @JoinTable(name= "pois_itineraries",
            joinColumns = @JoinColumn(name = "poi_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private List<Tag> tags;


    public Poi(PoiBuilder builder) {
        this.poiname =     builder.getPoiName();
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
                + "\"poiname\":\"" + poiname + "\","
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

    public String  getName() { return poiname; }

    public Boolean isLogical(){ return this.isLogical;}


    /** setters **/

    @Override
    public void setGeoLocation(GeoLocation geoLocation) {
        this.geoLocation = geoLocation;
    }

    @Override
    public void setMunicipality(Municipality municipality) {
        this.municipality = municipality;
    }
}
