package org.poifinder.models.activities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.poifinder.dataMappers.Views;
import org.poifinder.models.Content;
import org.poifinder.models.municipalities.Municipality;
import org.poifinder.models.poi.Poi;

import java.util.ArrayList;
import java.util.List;

/**
 * Questa classe astrae il concetto di attività intesa come contenuto
 * che aggrega uno o più punti di interesse coniugandoli in differenti
 * possibili utilizzi.
 */
@Getter
@Setter
@Entity
@Table(name="activities")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="h-type")
public class Activity extends Content {

    @JsonView(Views.Public.class)
    @Column(nullable = false, unique = true)
    private String name;

    @JsonView(Views.Public.class)
    @Column(length = 500)
    private String description;

    @JsonView(Views.Public.class)
    @Column(nullable = false)
    private String type;

    @ManyToOne
    @JoinColumn(name = "municipality_id", nullable = false)
    @JsonView(Views.Internal.class)
    private Municipality municipality;

    @ManyToMany
    @JsonView(Views.Public.class)
    @JoinTable(name= "activities_pois",
            joinColumns = @JoinColumn(name = "activity_id"),
            inverseJoinColumns = @JoinColumn(name = "poi_id"))
    private List<Poi> poiList;

    @Transient
    private Long municipality_id;

    public Activity(String name,
                    String description,
                    String type,
                    Municipality municipality) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.poiList = new ArrayList<Poi>();
        this.municipality = municipality;
    }

    public Activity(){ }

    public void addPoi(Poi poi){ this.poiList.add(poi); }

    public void removePoi(Poi poi){ this.poiList.remove(poi); }


    @Override
    public String toString() {
        return "{" +
                "\"name\": \"" + name + "\"," +
                "\"description\": \"" + (description != null ? description : "") + "\"," +
                "\"type\": \"" + type + "\"," +
                "\"municipality_id\": " + (municipality != null ? municipality.getId() : null) + "," +
                "\"poiList\": " + (poiList != null ? poiList.stream().map(poi -> poi.getId()).toList() : "[]") +
                "}";
    }

}
