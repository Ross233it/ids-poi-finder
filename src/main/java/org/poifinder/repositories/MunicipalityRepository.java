package org.poifinder.repositories;

import org.poifinder.models.municipalities.Municipality;
import org.poifinder.models.poi.Poi;
import org.poifinder.models.users.RegisteredUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MunicipalityRepository extends JpaRepository<Municipality, Long> {


    /**
     * Verifica se un comune con lo stesso nome esiste
     * @param name il nome del comune
     * @return true se esiste già, false altrimenti
     */
    boolean existsByName(String name);

    @Query("SELECT p FROM Poi p WHERE p.municipality.id = :municipalityId")
    List<Poi> findPoisByMunicipalityId(@Param("municipalityId") long municipalityId);


    @Query("SELECT m FROM Municipality m WHERE m.id = :municipalityId")
    Municipality getObjectById(@Param("municipalityId") long municipalityId);


    /**
     * Query di ricerca Comuni in base all'autore
     * @return List<Municipality> lista di punti di interesse ritrovata
     */
    List<Municipality>findByAuthor(RegisteredUser author);

    /**
     * Query di ricerca Comuni in base all'utente che lo ha pubblicato
     * @return List<Municipality> lista di punti di interesse ritrovata
     */
    List<Municipality> findByApprover(RegisteredUser approver);




    /**
     * Query di ricerca Comuni in base al nome  E al parametro "search"
     * @param municipality il nome del Comune da ricercare
     * @param search il parametro di ricerca
     * @return List<Municipality> lista di comuni attinenti alla ricerca
     */
    @Query("SELECT m FROM Municipality m WHERE m.name LIKE %:municipality% AND (m.name LIKE %:search% OR m.region LIKE %:search% OR m.province LIKE %:search%)")
    List<Municipality> searchByMunicipalityAndSearch(@Param("municipality") String municipality, @Param("search") String search);



    /**
     * Query di ricerca Comuni in base al nome del municipality
     * @param municipality il nome del Comune da ricercare
     * @return List<Municipality> lista di comuni attinenti alla ricerca
     */
    @Query("SELECT m FROM Municipality m WHERE m.name LIKE %:municipality%")
    List<Municipality> searchByMunicipality(@Param("municipality") String municipality);



    /**
     * Query di ricerca Comuni in base ad un termine di ricerca
     * @param search termine di ricerca
     * @return List<Municipality> lista di comuni attinenti alla ricerca
     */
    @Query("SELECT m FROM Municipality m WHERE m.name LIKE %:search% OR m.region LIKE %:search% OR m.province LIKE %:search%")
    List<Municipality> searchBySearch(@Param("search") String search);





//    @Query("SELECT new org.poifinder.dataMappers.municipality.MunicipalityListMapper(m.id, m.name, m.description, m.geoLocation) " +
//            "FROM Municipality m WHERE m.id = :municipalityId")
//                  MunicipalityListMapper getMunicipalityById(@Param("municipalityId") long municipalityId);


//    public MunicipalityRepository() {
//        super("municipalities");
//        this.queryBuilder = buildMainQuery();
//    }

//    @Override
//    public List<Map<String, Object>> index(String query) throws Exception {
//        return super.index(this.queryBuilder.toString());
//    }
//

    }
//
//    @Override
//    public Municipality create(Municipality municipality, String query) throws Exception {
//        query = "INSERT INTO "
//                + this.tableName +
//                " (name, province, region, geolocation_id, author_id) VALUES (?, ?, ?, ?, ?)";
//        return super.create(municipality, query);
//    }
//
//    @Override
//    public Municipality update(Municipality municipality, String query) throws Exception {
//        Object[] data = municipality.getData();
//
//        query = "UPDATE " + this.tableName + " " +
//                " SET " +
//                " name = ?, " +
//                " region = ?, " +
//                " province = ?, " +
//                " geolocation_id = ?," +
//                " author_id = ? " +
//                " WHERE id = "+ municipality.getId() +";";
//        DbUtilities.executeQuery(query, data);
//        return municipality;
//    }
//
//    /**
//     * Rimuove dalla persistenza il comune e tutti i dati a lui
//     * direttamente correlati.
//     * @param municiaplity D entity
//     * @return
//     * @throws SQLException
//     */
//    @Override
//    public int delete(Municipality municiaplity, String query) throws SQLException {
//        query = "DELETE M, G, P "+
//        "FROM municipalities AS M "+
//        "LEFT JOIN geolocations AS G ON M.geolocation_id = G.id "+
//        "LEFT JOIN pois AS P ON M.id = P.municipality_id " +
//        "WHERE M.id = ?; ";
//        return super.delete(municiaplity, query);
//    }
//}


