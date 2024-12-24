package org.httpServer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DbUtilities {
    /**
     * Mappa i dati di una query in formato JSON
     * @param resultSet
     * @return String JSON
     * @throws SQLException
     * @throws JsonProcessingException
     */
    public static String mapJsonData(ResultSet resultSet) throws SQLException, JsonProcessingException {
        List<Map<String, Object>> resultList = new ArrayList<>();
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();

        while (resultSet.next()) {
            Map<String, Object> row = new HashMap<>();
            for (int i = 1; i <= columnCount; i++) {
                String columnName  = metaData.getColumnName(i);
                Object columnValue = resultSet.getObject(i);
                row.put(columnName, columnValue);
            }
            resultList.add(row);
        }
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(resultList);
    }




}
