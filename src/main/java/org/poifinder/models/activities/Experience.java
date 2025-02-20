package org.poifinder.models.activities;

import jakarta.persistence.Entity;

import java.util.Arrays;

/**
 * Classe Experience che estende Activity
 * Un'esperienza è una raccolta di punti di interesse senza un ordine specifico.
 */
@Entity
public class Experience extends Activity {

    private String date;

    public Experience(String name, String description, String type, String eventDate) {
        super(name, description, type);
        this.getDate();
    }

    public Experience() {
        super();
    }

    /** Getters  **/
    @Override
    public Object[] getData() {
        Object[] data = super.getData();
        Object[] newData = Arrays.copyOf(data, data.length + 1);
        newData[data.length] = getDate();
        return newData;
    }

    /** Getters **/

    public String getDate() { return date; }

    /** Setters **/

    public void setDate(String date) { this.date = date; }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(super.toString());
        sb.append("{\"date\": \"").append(getDate()).append("\"} ");
        return sb.toString();
    }
}
