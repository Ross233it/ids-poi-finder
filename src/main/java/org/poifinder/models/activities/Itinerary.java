package org.poifinder.models.activities;

import jakarta.persistence.Entity;
import org.poifinder.models.poi.Poi;

import java.util.*;

/**
 * Classe Itinerary che estende Activity
 * Un itinerario è una sequenza ordinata di punti di interesse.
 */
@Entity
public class Itinerary extends Activity {
   
    private List<Poi> poiList;

    public Itinerary(String name, String description,String type, List<Long> orderedIds) {
        super(name, description, type);
        this.poiList = new ArrayList<>();
        this.orderPoi(orderedIds);
    }

    public Itinerary() {
        super();
        this.poiList = new ArrayList<>();
    }

    /**
     * Ordina i poi secondo una lista di id che identificano l'ordine
     * delle tappe.
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
