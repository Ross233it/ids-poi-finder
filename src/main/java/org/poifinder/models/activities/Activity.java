package org.poifinder.models.activities;

import jakarta.persistence.*;
import org.poifinder.models.Content;
import org.poifinder.models.municipalities.Municipality;
import org.poifinder.models.poi.Poi;
import org.poifinder.services.MunicipalityService;

import java.util.ArrayList;
import java.util.List;

/**
 * Questa classe astrae il concetto di attività intesa come contenuto volto a
 * promuovere l'interazione tra utenti o la partecipazione ad eventi.
 */

@Entity
@Table(name="activities")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
public class Activity extends Content {

    @Column(nullable = false)
    private String name;

    @Column(length = 500)
    private String description;

    @Column(nullable = false)
    private String type;


    @ManyToOne
    @JoinColumn(name = "municipality_id", nullable = false)
    private Municipality municipality;

    @ManyToMany
    @JoinTable(name= "activities_pois",
            joinColumns = @JoinColumn(name = "activity_id"),
            inverseJoinColumns = @JoinColumn(name = "poi_id"))
    private List<Poi> poiList;

    public Activity(String name, String description, String type, Municipality municipality) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.setStatus("pending");
        this.poiList = new ArrayList<Poi>();
        this.municipality = municipality;
    }

    public Activity(ActivityBuilder builder){
        this.poiList = new ArrayList<Poi>();
    }

    public Activity(){ }


    /** setters **/

    public void setName(String name) { this.name = name; }

    public void setDescription(String description) {this.description = description; }

    public void setPoiList(List<Poi> poiList) { this.poiList = poiList; }

    public void addPoi(Poi poi){ this.poiList.add(poi); }

    public void removePoi(Poi poi){ this.poiList.remove(poi); }
    /** getters **/


    @Override
    public String toString() {
        return "";
    }

    public String getName() { return name;}

    public String getDescription() { return description; }

    public String getType() { return type; }

    public List<Poi> getPoiList() { return this.poiList; }
}
