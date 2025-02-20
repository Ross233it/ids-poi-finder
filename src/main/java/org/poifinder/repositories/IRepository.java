package org.poifinder.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Map;

/**
 * Astrae il concetto di Persistenza dei dati. Le classi che implementano
 * questa interfaccia hanno la responsabilità di gestire le interazioni fra il
 * sistema e lo strato di persistenza.
 * @param <D> il tipo di oggetto che verrà gestito
 */

@NoRepositoryBean
public interface IRepository<D> extends JpaRepository<D, Long> {

    /**
     * Tutti i metodi della precedente interfaccia rimossi perchè crud già
     * presente in JpaRepository
     */


    /**
     * Ricerca un elemento in base ad una query ed un parametro di ricerca
     * @param filters i parametri di ricerca
     * @return
     * @throws Exception
     */
    List<D> search(Map<String, String> filters) throws Exception;
}
