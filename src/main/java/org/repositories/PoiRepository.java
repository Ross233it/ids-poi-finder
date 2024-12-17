package org.repositories;

import org.models.poi.BasePoi;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;


public class PoiRepository extends BaseRepository<BasePoi> implements Repository<BasePoi> {


    public PoiRepository(String tableName) {
        super(tableName);
    }

    @Override
    public BasePoi readById(int id) throws SQLException {
        return null;
    }

//    @Override
    public BasePoi create(BasePoi poi) throws Exception {
        if (poi == null) {
            throw new IllegalArgumentException("L'entity non può essere null.");
        }
        int geolocationId = poi.getGeoLocation().getId();
        List<String> columns = Arrays.asList("name", "description", "is_logical", "geolocation_id");
        Object[] data = poi.getData();
        Object[] newData = Arrays.copyOf(data, data.length + 1);
        newData[newData.length - 1] = geolocationId;
        super.create(columns, newData);
        return poi;
    }

    @Override
    public Boolean update(BasePoi entity) throws SQLException {
        return null;
    }

    @Override
    public int delete(int id) throws SQLException {
        return 0;
    }
}
