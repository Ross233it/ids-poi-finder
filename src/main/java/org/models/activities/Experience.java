package org.models.activities;

import org.models.poi.Poi;
import org.models.users.RegisteredUser;

import java.util.Set;
import java.util.HashSet;

/**
 * Classe Experience che estende Activity
 * Un'esperienza è una raccolta di punti di interesse senza un ordine specifico.
 */
public class Experience extends Activity {

    private Set<Poi> poiSet;

    public Experience(String name, String description, RegisteredUser author) {
        super(name, description, author);
        this.poiSet = new HashSet<>();
    }

    /** Getters  **/
    @Override
    public Object[] getData() {
        return new Object[]{
                getName(),
                getDescription(),
                getStatus(),
                getAuthor(),
                getValidator(),
                poiSet
        };
    }

    public Set<Poi> getPoiSet() { return poiSet; }

    /** Setters **/
    public void setPoiSet(Set<Poi> poiSet) { this.poiSet = poiSet; }

    public void addPoi(Poi poi) {
        poiSet.add(poi);
    }

    public void removePoi(Poi poi) {
        poiSet.remove(poi);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{")
                .append("\"name\": \"").append(getName()).append("\", ")
                .append("\"description\": \"").append(getDescription()).append("\", ")
                .append("\"status\": \"").append(getStatus()).append("\", ")
                .append("\"author\": \"").append(getAuthor().getUsername()).append("\", ")
                .append("\"validator\": \"").append(getValidator() != null ? getValidator().getUsername() : "null").append("\", ")
                .append("\"poiSet\": [");

        for (Poi poi : poiSet) {
            sb.append(poi.toString()).append(", ");
        }   
        if (!poiSet.isEmpty()) sb.delete(sb.length() - 2, sb.length());
        sb.append("]}");
        return sb.toString();
    }
}
