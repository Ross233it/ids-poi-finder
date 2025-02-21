package org.poifinder.dataMappers.poi;

import org.poifinder.dataMappers.DataMapper;
import org.poifinder.models.GeoLocation;
import org.poifinder.models.poi.Poi;

public class PoiListMapper extends DataMapper<Poi> {

    private Long  id;

    private String  poiname;

    private String  description;

    private String  type;

    private Boolean isLogical;

    private GeoLocation geoLocation;

    public PoiListMapper(Long id,
                         String poiname,
                         String description,
                         String type,
                         Boolean isLogical,
                         GeoLocation geoLocation) {
        this.poiname = poiname;
        this.description = description;
        this.type = type;
        this.isLogical = isLogical;
        this.geoLocation = geoLocation;
    }

    public String getPoiname() {
        return poiname;
    }

    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
    }

    public Boolean getLogical() {
        return isLogical;
    }
}
