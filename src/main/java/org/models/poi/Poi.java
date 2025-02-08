package org.models.poi;

import org.dataMappers.DataMapper;
import org.models.municipalities.Municipality;
import org.models.taxonomy.Category;
import org.models.GeoLocation;
import org.models.taxonomy.Tag;
import java.util.List;
import java.util.stream.Collectors;

public class Poi extends  IPoi {
    private String  poiname;

    private String  description;

    private String  type;

    private Boolean isLogical = false;

    private Municipality municipality = null;

    private GeoLocation geoLocation = null;

    private List<Tag> tags;

    private List<Category> categories;

    private String status;

    public Poi(PoiBuilder builder) {
        this.poiname =     builder.getPoiName();
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


    @Override
    public String toString() {
        String resultString = "{"
                + "\"id\":\"" + getId() + "\","
                + "\"poiname\":\"" + poiname + "\","
                + "\"description\":\"" + description + "\","
                + "\"type\":\"" + type + "\","
                + "\"isLogical\":\"" + isLogical + "\","
                + "\"status\":\"" + status + "\""
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

    /**
     * Restituisce i dati dell'oggetto
     * @return Object[] array di oggetti
     */
    @Override
    public Object[] getData(){

        return new Object[] {
            this.poiname,
            this.description,
            this.type,
            this.status,
            this.isLogical,
            this.municipality.getId(),
            this.geoLocation.getId(),
            this.getAuthor() != null ? this.getAuthor().getId() : null,
            this.getApprover() != null ? this.getApprover().getId() : null
        };
    }

    @Override
    public Municipality getMunicipality() {return municipality;}

    @Override
    public GeoLocation getGeoLocation() { return geoLocation; }

    public String  getName()       { return poiname; }

    public String  getStatus()       { return status; }

    /** setters **/
    @Override
    public void setGeoLocation(GeoLocation geoLocation) {
        this.geoLocation = geoLocation;
    }

    @Override
    public void setMunicipality(Municipality municipality) {this.municipality = municipality;}

    public void setStatus(String status) { this.status = status; }

}
