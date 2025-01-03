package org.models.activities;

import org.models.poi.Poi;
import org.models.users.RegisteredUser;

import java.util.List;
import java.util.ArrayList;

/**
 * Classe Itinerary che estende Activity
 * Un itinerario è una sequenza ordinata di punti di interesse.
 */
public class Itinerary extends Activity {
   
    private List<Poi> poiList;

    public Itinerary(String name, String description, RegisteredUser author) {
        super(name, description, author);
        this.poiList = new ArrayList<>();
    }

    /** Getters **/
    @Override
    public Object[] getData() {
        return new Object[] {
                getName(),
                getDescription(),
                getStatus(),
                getAuthor(),
                getValidator(),
                poiList
        };
    }
    public List<Poi> getPoiList() { return poiList; }

    /** Setters **/
    public void setPoiList(List<Poi> poiList) { this.poiList = poiList; }

    /** Metodi aggiuntivi **/
    public void addPoi(Poi poi) { poiList.add(poi); }

    public void removePoi(Poi poi) { poiList.remove(poi); }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{")
                .append("\"name\": \"").append(getName()).append("\", ")
                .append("\"description\": \"").append(getDescription()).append("\", ")
                .append("\"status\": \"").append(getStatus()).append("\", ")
                .append("\"author\": \"").append(getAuthor().getUsername()).append("\", ")
                .append("\"validator\": \"").append(getValidator() != null ? getValidator().getUsername() : "null").append("\", ")
                .append("\"poiList\": [");

        for (int i = 0; i < poiList.size(); i++) {
            sb.append(poiList.get(i).toString());
            if (i < poiList.size() - 1) {
                sb.append(", ");
            }
        }
        sb.append("]}");
        return sb.toString();
    }
}
