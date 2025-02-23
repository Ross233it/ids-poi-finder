package org.poifinder.dataMappers.poi;

import org.poifinder.dataMappers.DataMapper;
import org.poifinder.models.poi.Poi;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Primary
public class PoiMapper implements DataMapper {
    private String  poiname;

    private String  description;

    private String  type;

    private Boolean isLogical = false;

}
