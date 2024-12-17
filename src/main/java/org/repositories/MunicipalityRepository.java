package org.repositories;

import org.models.Municipality;
import org.models.poi.BasePoi;

import java.sql.*;
import java.util.Arrays;
import java.util.List;

public class MunicipalityRepository extends BaseRepository<Municipality> implements Repository<Municipality> {

    private String tableName = "municipalities";


    public MunicipalityRepository(String tableName) {
        super(tableName);
    }

    @Override
    public Municipality readById(int id) throws SQLException {
        return null;
    }

    @Override
    public Boolean update(Municipality entity) throws SQLException {
        return null;
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
        super.create(columns, newData);
        return municipality;
    }

//    public Municipality create(Municipality municipality) throws SQLException {
//            String name = municipality.getName();
//            String query = "INSERT INTO municipality (name, geolocation_id) VALUES (?, ?)";
//
//            try (Connection connection = this.dbConnectionManager.openConnection();
//                 PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)){
//                    preparedStatement.setString(1, municipality.getName());
//                    preparedStatement.setInt(2, municipality.getGeoLocation().getId());
//                int recordNum = preparedStatement.executeUpdate();
//
//                if (recordNum > 0) {
//                    try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
//                        if (generatedKeys.next()) {
//                            int generatedId = generatedKeys.getInt(1);
//                            municipality.setId(generatedId);
//                        }
//                    }
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//            return municipality;
//        }

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


