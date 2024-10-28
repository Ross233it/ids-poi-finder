package org.models.poi;

import org.models.informations.PoiDetail;

public class LogicalPoi extends PhisicalPoi {

    private Boolean logical_poi = true;

    public LogicalPoi(String name, PoiDetail poiDetails){
        super(name, poiDetails);
    }
}
