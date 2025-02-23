package org.poifinder.dataMappers.poi;

import lombok.Getter;
import lombok.Setter;
import org.poifinder.dataMappers.DataMapper;
import org.poifinder.models.GeoLocation;


@Getter
@Setter
public class PoiListMapper implements DataMapper{

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

}
