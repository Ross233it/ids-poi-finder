package org.models.poi;

import org.httpServer.http.HttpResponses;
import org.models.municipalities.Municipality;
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
        this.setApprover(builder.getApprover());
        this.setAuthor(builder.getAuthor());
    }

    /**
     * Restituisce una rappresentazione testuale dell'oggetto
     * @return String rappresentazione testuale
     */
    @Override
    public String toString() {
        String poiString = "{ }";
        try {
            poiString = HttpResponses.objectToJson(this);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return "{" +
                poiString +
                "\"municipality\": " + (municipality != null ? municipality.toString() : "{ }") +
                "\"geoLocation\": " + (geoLocation != null ? geoLocation.toString() : "{ }") +
                "\"author\": " + (getAuthor() != null ? getAuthor().toString() : "{ }") +
                "\"author\": " + (getApprover() != null ? getApprover().toString() : "{ }") +
                "}";
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
            this.geoLocation.getId(),
            this.getAuthor().getId(),
            this.getApprover().getId()
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

}
