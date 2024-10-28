package org.models.poi;

import org.models.informations.PoiDetail;

public interface Poi{

    public long getId();

    public void setId(long id);

    public String getName();

    public void setName(String name);

    public PoiDetail getPoiDetails();

    public void setPoiDetails(PoiDetail poiDetails, Boolean isLogical);
}
