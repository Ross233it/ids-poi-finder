package org.poifinder.repositories;

import org.poifinder.httpServer.DbUtilities;
import org.poifinder.httpServer.auth.UserContext;
import org.poifinder.models.activities.Activity;
import org.poifinder.models.poi.Poi;
import org.poifinder.models.users.RegisteredUser;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Ha la responsabilità di gestire la persistenza dei dati in relazione ai Point of Interest.
 * Costruisce le query specifiche per la gestione dei Point of Interest.
 */
@Repository
@Primary
public interface PoiRepository extends JpaRepository<Poi, Long> {










    /**
     * Ritorna le informazioni di tutti i punti di interesse relativi ad un comune
     * @param id l'identificativo unico del comune
     * @return Map<String, Object> lista di punti di interesse
     * @throws Exception
     */
//    public List<Map<String, Object>> getByMunicipalityId(Long id) throws Exception{
//        String query = "";
//
//        RegisteredUser currentUser = UserContext.getCurrentUser();
//        if(currentUser != null && currentUser.getRole() != null)
//            query = this.queryBuilder.append("WHERE P.municipality_id = ?;").toString();
//        else
//            query = this.queryBuilder.append("WHERE P.municipality_id = ? AND  P.status = 'published';").toString();
//        Object[] data = new Object[]{id};
//        List<Map<String, Object>> resultSet = DbUtilities.executeSelectQuery(query, data);
//        if (resultSet.isEmpty()) {
//            return null;
//        }
//        return resultSet;
//    }

//    @Override
//    public  int delete(Poi entity, String query)  throws SQLException {
//        long id = entity.getId();
//        Object[] data = new Object[]{id};
//
//        query =
//        "DELETE P, G "+
//        "FROM pois AS P "+
//        "LEFT JOIN geolocations AS G ON G.id = P.geolocation_id "+
//        "WHERE P.id = ?; " ;
//        return DbUtilities.executeQuery(query, data);
//    }
}
