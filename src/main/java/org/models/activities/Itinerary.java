package org.models.activities;

import org.models.poi.IPoi;
import org.models.poi.Poi;
import org.models.users.RegisteredUser;

import java.util.*;
import java.util.stream.Stream;

/**
 * Classe Itinerary che estende Activity
 * Un itinerario è una sequenza ordinata di punti di interesse.
 */
public class Itinerary extends Activity {
   
    private List<Poi> poiList;

    public Itinerary(String name, String description,String type, List<Long> orderedIds) {
        super(name, description, type);
        this.poiList = new ArrayList<>();
        this.orderPoi(orderedIds);
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
