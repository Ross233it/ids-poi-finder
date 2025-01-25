package org.models.municipalities;

import org.models.Content;
import org.models.GeoLocation;
import org.models.poi.IPoi;

import java.util.ArrayList;

public class Municipality extends Content {

    private String name;
    private String region;
    private String province;

    private GeoLocation geoLocation;

    private ArrayList<IPoi> pois;

    public Municipality(String name, String region, String province) {
        this.name = name;
        this.region = region;
        this.province = province;
        this.pois = new ArrayList<>();
    };

    public Municipality(String name, GeoLocation geoLocation) {
        this.name = name;
        this.geoLocation = geoLocation;
        this.pois = new ArrayList<>();
    }

    public Municipality(MunicipalityBuilder municipalityBuilder) {
        super();
    }

    /**
     * Restituisce una rappresentazione testuale dell'oggetto
     * @return String rappresentazione testuale
     */
    @Override
    public String toString() {
        return "{" +
                "\"name\": \"" + name + "\"," +
                "\"region\": \"" + region + "\"," +
                "\"province\": \"" + province + "\"," +
                "\"geoLocation\": " + (geoLocation != null ? geoLocation.toString() : "null") +
                "}";
    }

    /** getters **/

    @Override
    public Object[]  getData(){
        return new Object[]{
                this.getName(),
                this.getRegion(),
                this.getProvince(),
                this.getGeoLocation().getId(),
        };
    }

    public String getName()          { return name; }

    public String getProvince()      { return province; }

    public String getRegion()        { return region;}

    public ArrayList<IPoi> getPois() { return pois; }

    public GeoLocation getGeoLocation() { return this.geoLocation;}


    /** setters **/

    public void setName(String name) { this.name = name; }

    public void setGeoLocation(GeoLocation geoLocation) {
        this.geoLocation = geoLocation;
    }

    /**
     * Aggiunge un punto di interesse alla lista
     * @param poi punto di interesse da aggiungere
     */
    public void addPoi(IPoi poi) { this.pois.add(poi); }

    /**
     * Rimuove un punto di interesse dalla lista
     * @param poi punto di interesse da rimuovere
     */
    public void removePoi(IPoi poi) { this.pois.remove(poi); }

}
