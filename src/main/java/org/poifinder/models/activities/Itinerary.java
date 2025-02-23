package org.poifinder.models.activities;

import jakarta.persistence.*;
import org.poifinder.models.municipalities.Municipality;
import org.poifinder.models.poi.Poi;

import java.util.*;

/**
 * Classe Itinerary che estende Activity
 * Un itinerario è una sequenza ordinata di punti di interesse.
 */
@Entity
@DiscriminatorValue("itinerary")
public class Itinerary extends Activity {

    @ManyToMany
    @JoinTable(name= "pois_itineraries",
            joinColumns = @JoinColumn(name = "poi_id"),
            inverseJoinColumns = @JoinColumn(name = "itinerary_id"))
    private List<Poi> poiList;


    public Itinerary(String name,
                     String description,
                     Municipality municipality) {
        super(name, description, "itinerary", municipality);
    }

    public Itinerary() {
        super();
    }

    /**
     * Ordina i poi dell'itinerario secondo una lista di id che
     * identificano l'ordine delle tappe.
     * @param orderedIds
     */
    private void orderPoi(List<Long> orderedIds) {
        Map<Long, Poi> poiMap = new HashMap<>();
        for (Poi poi : poiList) {
            poiMap.put(poi.getId(), poi);
        }

        List<Poi> orderedPoiList = new ArrayList<>();
        for (Long id : orderedIds) {
            if (poiMap.containsKey(id)) {
                orderedPoiList.add(poiMap.get(id));
            }
        }
        this.poiList = orderedPoiList;
    }
}
