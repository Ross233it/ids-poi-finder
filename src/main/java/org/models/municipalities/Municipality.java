package org.models.municipalities;

import org.httpServer.http.HttpResponses;
import org.models.Content;
import org.models.GeoLocation;
import org.models.poi.Poi;


import java.util.ArrayList;

public class Municipality extends Content {

    private String name;
    private String region;
    private String province;
    private String status;

    private GeoLocation geoLocation;

    private ArrayList<Poi> pois;

    public Municipality(MunicipalityBuilder builder){
        this.name = builder.getName();
        this.region = builder.getRegion();
        this.province = builder.getProvince();
        this.status = builder.getStatus();
        this.geoLocation = builder.getGeoLocation();
        this.setAuthor(builder.getAuthor());
        this.setApprover(builder.getApprover());
        this.pois = builder.getPois();
    }

    /**
     * Restituisce una rappresentazione testuale dell'oggetto
     * @return String rappresentazione testuale
     */
    @Override
    public String toString() {
        String municipalityString = "{ }";
        try {
            municipalityString = HttpResponses.objectToJson(this);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return municipalityString;
    }

    /** getters **/

    @Override
    public Object[]  getData(){
        return new Object[]{
                this.getName(),
                this.getRegion(),
                this.getProvince(),
                this.getGeoLocation().getId(),
                this.getAuthor().getId()
        };
    }

    public String getName()          { return name; }

    public String getProvince()      { return province; }

    public String getRegion()        { return region;}

    public ArrayList<Poi> getPois() { return pois; }

    public GeoLocation getGeoLocation() { return this.geoLocation;}


    /** setters **/

    public void setName(String name) { this.name = name; }

    public void setGeoLocation(GeoLocation geoLocation) {
        this.geoLocation = geoLocation;
    }

    public void setPois(ArrayList<Poi> pois) { this.pois = pois; }

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
