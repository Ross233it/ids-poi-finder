package org.models.activities;

import jakarta.persistence.*;
import org.models.Content;
import org.models.poi.Poi;

import java.util.ArrayList;
import java.util.List;

/**
 * Questa classe astrae il concetto di attività intesa come contenuto volto a
 * promuovere l'interazione tra utenti o la partecipazione ad eventi.
 */

@Entity
@Table(name="activities")
public class Activity extends Content {

    @Column(nullable = false)
    private String name;

    @Column(length = 500)
    private String description;

    @Column(nullable = false)
    private String type;

    @OneToMany
    @JoinColumn(name = "activity_id")
    private List<Poi> poiList;

     public Activity(String name, String description, String type) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.setStatus("pending");
        this.poiList = new ArrayList<Poi>();
    }


    public Activity(ActivityBuilder builder){
        this.poiList = new ArrayList<Poi>();
    }

    public Activity() { }


    /** setters **/

    public void setName(String name) { this.name = name; }

    public void setDescription(String description) {this.description = description; }

    public void setPoiList(List<Poi> poiList) { this.poiList = poiList; }

    public void addPoi(Poi poi){ this.poiList.add(poi); }

    public void removePoi(Poi poi){ this.poiList.remove(poi); }
    /** getters **/

    /**
     * Restituisce i dati dell'oggetto
     * @return Object[] array di oggetti
     */
    @Override
    public Object[] getData() {
        return new Object[] {
            this.getName(),
            this.getDescription(),
            this.getType(),
            this.getPoiList(),
            this.getStatus() != null ? this.getStatus() : null,
            this.getAuthor() != null ? this.getAuthor().getId() : null,
            this.getApprover() != null ? this.getApprover().getId() : null
        };
    }

    @Override
    public String toString() {
        return "";
    }

    public String getName() { return name;}

    public String getDescription() { return description; }

    public String getType() { return type; }

    public List<Poi> getPoiList() { return this.poiList; }
}
