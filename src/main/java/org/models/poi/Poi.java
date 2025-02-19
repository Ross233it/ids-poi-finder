package org.models.poi;

import org.models.Content;
import org.models.municipalities.Municipality;
import org.models.taxonomy.Category;
import org.models.GeoLocation;
import org.models.taxonomy.Tag;
import java.util.List;

public class Poi extends Content {
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
        this.type =        builder.getType();
        this.setStatus(builder.getStatus());
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
            this.getStatus() != null ? this.getStatus() : null,
            this.isLogical,
            this.municipality.getId(),
            this.geoLocation.getId(),
            this.getAuthor() != null ? this.getAuthor().getId() : null,
            this.getApprover() != null ? this.getApprover().getId() : null
        };
    }

    public String getStatus() {return this.status; }
//    @Override
    public Municipality getMunicipality() {return municipality;}

//    @Override
    public GeoLocation getGeoLocation() { return geoLocation; }

    public String  getName()       { return poiname; }

    public Boolean isLogical(){ return this.isLogical;}



    /** setters **/
//    @Override
    public void setStatus(String status) {this.status = status; }


//    @Override
    public void setGeoLocation(GeoLocation geoLocation) {
        this.geoLocation = geoLocation;
    }

//    @Override
    public void setMunicipality(Municipality municipality) {this.municipality = municipality;}

}
