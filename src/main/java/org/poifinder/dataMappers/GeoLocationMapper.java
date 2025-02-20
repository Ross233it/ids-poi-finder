package org.poifinder.dataMappers;

import org.poifinder.models.GeoLocation;

import java.util.Map;


public class GeoLocationMapper extends DataMapper<GeoLocation> {

    @Override
    public GeoLocation mapDataToObject(Map<String, Object> result) {
        if(result.containsKey("geoLocation")) {
            result = (Map<String, Object>) result.get("geoLocation");
            result.put("G_id", result.get("id"));
        }
        double def =  0;
        Object latitudeObj = result.getOrDefault("latitude", def);
        Object longitudeObj = result.getOrDefault("longitude", def);
        double latitude = latitudeObj instanceof Number ? ((Number) latitudeObj).doubleValue() : def;
        double longitude = longitudeObj instanceof Number ? ((Number) longitudeObj).doubleValue() : def;

        GeoLocation geoLocation = new GeoLocation(
                (String) result.getOrDefault("address", null),
                (String) result.getOrDefault("number", null),
                (String) result.getOrDefault("cap",null),
                latitude,
                longitude
        );

        if(result.containsKey("G_id")){
            long id = castIdvalue(result.get("G_id"));
            geoLocation.setId(id);
        }

        return geoLocation;
    }
}
