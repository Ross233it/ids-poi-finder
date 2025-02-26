package org.poifinder.models.municipalities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.poifinder.dataMappers.Views;
import org.poifinder.models.Content;
import org.poifinder.models.GeoLocation;
import org.poifinder.models.IModel;
import org.poifinder.models.activities.Activity;
import org.poifinder.models.poi.Poi;


import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name="municipalities")
public class Municipality extends Content implements IModel {

    @JsonView(Views.Public.class)
    private String name;

    @JsonView(Views.Public.class)
    private String region;

    @JsonView(Views.Public.class)
    private String province;

    @JsonView(Views.Public.class)
    private String status = "published";

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonView(Views.Public.class)
    @JoinColumn(name = "geolocation_id", referencedColumnName = "id", nullable = false)
    private GeoLocation geoLocation;

    @OneToMany(mappedBy = "municipality", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonView(Views.Public.class)
    @JsonManagedReference
    private List<Poi> pois;

    @OneToMany(mappedBy = "municipality", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonView(Views.Internal.class)
    private List<Activity> activities = new ArrayList<>();

    public Municipality(MunicipalityBuilder builder){
        this.name = builder.getName();
        this.region = builder.getRegion();
        this.province = builder.getProvince();
        this.geoLocation = builder.getGeoLocation();
        this.setAuthor(builder.getAuthor());
        this.setApprover(builder.getApprover());
        this.pois = builder.getPois();
    }

    public Municipality() {
        this.pois = new ArrayList<>();
    }

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
