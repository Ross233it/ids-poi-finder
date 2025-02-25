package org.poifinder.repositories;

import org.poifinder.models.poi.Poi;
import org.poifinder.models.users.RegisteredUser;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Ha la responsabilità di gestire la persistenza dei dati in relazione ai Point of Interest.
 * Costruisce le query specifiche per la gestione dei Point of Interest.
 */
@Repository
@Primary
public interface PoiRepository extends JpaRepository<Poi, Long> {

    /**
     * Ritorna un poi identificato dal proprio id
     * @param poiId
     * @return
     */
    @Query("SELECT p FROM Poi p WHERE p.id = :poiId")
    Poi getObjectById(@Param("poiId") long poiId);


    /**
     * Query di ricerca Poi in base all'autore
     * @return List<Poi> lista di punti di interesse ritrovata
     */
    List<Poi> findByAuthor(RegisteredUser author);

    /**
     * Query di ricerca Poi in base all'utente che lo ha pubblicato
     * @return List<Poi> lista di punti di interesse ritrovata
     */
    List<Poi> findByApprover(RegisteredUser approver);


    /**
     * Query di ricerca Poi in base al nome del municipality E al parametro "search"
     * @param municipality il nome del municipality di appartenenza del poi
     * @param search il parametro di ricerca
     * @return List<Poi> lista di punti di interesse ritrovata
     */
    @Query("SELECT p FROM Poi p WHERE p.municipality.name LIKE %:municipality% AND (p.name LIKE %:search% OR p.description LIKE %:search%)")
    List<Poi> searchByMunicipalityAndSearch(@Param("municipality") String municipality, @Param("search") String search);


    /**
     * Query di ricerca Poi in base al nome del municipality
     * @param municipality il nome del municipality di appartenenza del poi
     * @return List<Poi> lista di punti di interesse ritrovata
     */
    @Query("SELECT p FROM Poi p WHERE p.municipality.name LIKE %:municipality%")
    List<Poi> searchByMunicipality(@Param("municipality") String municipality);


    /**
     * Query di ricerca Poi in base ad un termine di ricerca
     * @param search termine di ricerca
     * @return List<Poi> lista di punti di interesse ritrovata
     */
    @Query("SELECT p FROM Poi p WHERE p.name LIKE %:search% OR p.description LIKE %:search%")
    List<Poi> searchBySearch(@Param("search") String search);


    /**
     * Verifica se un poi esiste gia nel database in base al nome
     * @param name il nome del poi di cui verificare l'esistenza
     * @return true se esiste, false altrimenti
     */
    boolean existsByName(String name);


    /**
     * Ritorna le informazioni di tutti i punti di interesse relativi ad un municipality
     * @param id l'identificativo unico del municipality
     * @return Map<String, Object> lista di punti di interesse
     * @throws Exception
     */

    @Query("SELECT p FROM Poi p WHERE p.municipality.id = :municipalityId")
    List<Poi> getByMunicipalityId(@Param("municipalityId") long municipalityId);

}
