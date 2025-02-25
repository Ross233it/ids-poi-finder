package org.poifinder.models.users;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.poifinder.dataMappers.Views;
import org.poifinder.models.IModel;
import org.poifinder.models.activities.Activity;
import org.poifinder.models.poi.Poi;

import java.util.ArrayList;
import java.util.List;


/**
 * Classe che rappresenta una lista di contenuti preferiti associati
 * ad un utente. Tipologie poi e activity
 */
@Getter
@Setter
@Entity
@Table(name="favorite_contents")
public class FavoriteContents implements IModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany
    @JoinTable(name= "favorites_pois",
            joinColumns = @JoinColumn(name = "favorite_id"),
            inverseJoinColumns = @JoinColumn(name = "poi_id"))
    @JsonView(Views.Public.class)
    private List<Poi> favoritePois;

    @ManyToMany
    @JoinTable(name= "favorites_activities",
            joinColumns = @JoinColumn(name = "favorite_id"),
            inverseJoinColumns = @JoinColumn(name = "activity_id"))
    @JsonView(Views.Public.class)
    private List<Activity> favoriteActivities;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private RegisteredUser user;


    public FavoriteContents() {}

    public FavoriteContents(RegisteredUser user){
        this.user = user;
        this.favoriteActivities = new ArrayList<>();
        this.favoritePois = new ArrayList<>();
    }

    /**
     * Aggiunge una attività ai preferiti
     * @param activity l'attività da aggiungere
     */
    public void addContent(Activity activity){
        this.favoriteActivities.add(activity);
    }

    /**
     * Rimuove un contenuto dai preferiti
     * @param activity l'attività da rimuovere.
     */
    public void removeContent(Activity activity){
        this.favoriteActivities.remove(activity);
    }

    /**
     * Aggiunge un poi ai preferiti
     * @param poi il poi da aggiungere ai preferiti
     */
    public void addContent(Poi poi){
        this.favoritePois.add(poi);
    }

    /**
     * Rimuove un poi dai preferiti
     * @param poi il poi da rimuovere
     */
    public void removeContent(Poi poi){
        this.favoritePois.remove(poi);
    }

}
