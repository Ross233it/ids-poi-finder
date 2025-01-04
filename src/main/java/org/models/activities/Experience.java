package org.models.activities;

import org.models.poi.IPoi;
import org.models.users.RegisteredUser;

import java.util.Set;
import java.util.HashSet;

/**
 * Classe Experience che estende Activity
 * Un'esperienza è una raccolta di punti di interesse senza un ordine specifico.
 */
public class Experience extends Activity {

    private Set<IPoi> poiSet;

    private String date;

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

    /** Getters **/

    public Set<IPoi> getPoiSet() { return poiSet; }

    public String getDate() { return date; }



    /** Setters **/

    public void setPoiSet(Set<IPoi> poiSet) { this.poiSet = poiSet; }

    public void setDate(String date) { this.date = date; }



    /** Metodi aggiuntivi **/

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

        for (IPoi poi : poiSet) {
            sb.append(poi.toString()).append(", ");
        }   
        if (!poiSet.isEmpty()) sb.delete(sb.length() - 2, sb.length());
        sb.append("]}");
        return sb.toString();
    }

    @Override
    public void addPoi(IPoi poi) { poiSet.add(poi); }

    @Override
    public void removePoi(IPoi poi) { poiSet.remove(poi); }
}
