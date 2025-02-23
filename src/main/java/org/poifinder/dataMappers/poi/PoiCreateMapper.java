package org.poifinder.dataMappers.poi;

import lombok.Getter;
import lombok.Setter;
import org.poifinder.dataMappers.DataMapper;
import org.poifinder.dataMappers.GeoLocationMapper;

@Getter
@Setter
public class PoiCreateMapper implements DataMapper {

    private String name;

    private String description;

    private Boolean isLogical;

    private String type;

    private Long municipality_id;

    private GeoLocationMapper geoLocation;

}
