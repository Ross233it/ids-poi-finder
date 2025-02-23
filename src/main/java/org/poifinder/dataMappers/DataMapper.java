package org.poifinder.dataMappers;

import jakarta.persistence.MappedSuperclass;
import org.springframework.stereotype.Component;


/**
 * Questa classe ha la responsabilità di "tradurre" i dati del baseRepository
 * in oggetti e viceversa.
 */
@Component
@MappedSuperclass
public interface DataMapper {


    /**
     * Mappa una serie di dati provenienti dallo strato di persistenza
     * in una lista di oggetti della classe
     * @param results struttura dati contenenete informazioni sull'oggetto
     * @return istanza dell'oggetto
     */

}
