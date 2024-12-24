package org.repositories;

import org.models.Municipality;

import java.sql.*;
import java.util.Arrays;
import java.util.List;

public class MunicipalityRepository extends Repository<Municipality> implements IRepository<Municipality> {

    private String tableName = "municipalities";


    public MunicipalityRepository(String tableName) {
        super(tableName);
    }


    //    @Override
    public Municipality create(Municipality municipality) throws Exception {
        if (municipality == null) {
            throw new IllegalArgumentException("L'entity non può essere null.");
        }
        int geolocationId = municipality.getGeoLocation().getId();
        List<String> columns = Arrays.asList("name", "geolocation_id");
        Object[] data = municipality.getData();
        Object[] newData = Arrays.copyOf(data, data.length + 1);
        newData[newData.length - 1] = geolocationId;
        super.save(columns, newData);
        return municipality;
    }

    /**
     * Ritorna il record a db in base all'id
     * @param id l'id
     * @return ResultSet resultSet
     * @throws SQLException
     */
//    public ResultSet getById(Integer id) throws SQLException {
//            return super.getById(id, this.tableName);
//    }
}


