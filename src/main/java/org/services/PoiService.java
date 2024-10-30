package org.services;

import org.models.poi.Poi;

import java.util.Map;

public class PoiService implements Service<Poi> {

    public Poi create(Map<String, Object> objectData) {
            return new Poi("first poi", );
    };

}
