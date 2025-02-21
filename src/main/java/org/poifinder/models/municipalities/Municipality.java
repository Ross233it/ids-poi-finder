package org.poifinder.models.municipalities;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import org.antlr.v4.runtime.misc.NotNull;
import org.poifinder.dataMappers.Views;
import org.poifinder.models.Content;
import org.poifinder.models.GeoLocation;
import org.poifinder.models.IModel;
import org.poifinder.models.activities.Activity;
import org.poifinder.models.poi.Poi;


import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="municipalities")
public class Municipality extends Content implements IModel {

    @JsonView(Views.Public.class)
    private String name;

    @JsonView(Views.Public.class)
    private String region;

    @JsonView(Views.Public.class)
    private String province;

    @JsonView(Views.Public.class)
    private String status;

    @OneToOne
    @JsonView(Views.Public.class)
    private GeoLocation geoLocation;

    @OneToMany(mappedBy = "municipality", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonView(Views.Internal.class)
    private List<Poi> pois;

    @OneToMany(mappedBy = "municipality", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonView(Views.Internal.class)
    private List<Activity> activities = new ArrayList<>();

    public Municipality(MunicipalityBuilder builder){
        this.name = builder.getName();
        this.region = builder.getRegion();
        this.province = builder.getProvince();
        this.geoLocation = builder.getGeoLocation();
        this.setAuthor(builder.getAuthor());
        this.setApprover(builder.getApprover());
        this.pois = builder.getPois();
    }


    public Municipality() {
        this.pois = new ArrayList<>();
    }

    /**
     * Restituisce una rappresentazione testuale dell'oggetto
     * @return String rappresentazione testuale
     */
    @Override
    public String toString() {
        String resultString = "{"
                + "\"name\":\"" + name + "\","
                + "\"region\":\"" + region + "\","
                + "\"province\":\"" + province + "\","
                ;
        if(getId() != 0)
            resultString += "\"id:\""+getId();
        if(!pois.isEmpty()){
            resultString += ",\"pois\": {";
            for(Poi poi : pois)
                resultString += poi.toString()+",";
            resultString +="}";
        }
        if(geoLocation != null)
            resultString += ",\"geoLocation\": "+ geoLocation.toString();
        if(getAuthor() != null)
            resultString += ",\"author\": " + getAuthor().toString();
        return resultString +     "}";
    }

    /** getters **/

    public String getName()          { return name; }

    public String getProvince()      { return province; }

    public String getRegion()        { return region;}

    public List<Poi> getPois() { return pois; }

    public GeoLocation getGeoLocation() { return this.geoLocation;}


    /** setters **/

    public void setName(String name) { this.name = name; }

    public void setGeoLocation(GeoLocation geoLocation) {
        this.geoLocation = geoLocation;
    }

    public void setPois(List<Poi> pois) { this.pois = pois; }

    /**
     * Aggiunge un punto di interesse alla lista
     * @param poi punto di interesse da aggiungere
     */
    public void addPoi(Poi poi) { this.pois.add(poi); }

    /**
     * Rimuove un punto di interesse dalla lista
     * @param poi punto di interesse da rimuovere
     */
    public void removePoi(Poi poi) { this.pois.remove(poi); }

}
