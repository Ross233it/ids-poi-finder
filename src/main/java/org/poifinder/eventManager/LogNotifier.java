package org.poifinder.eventManager;

import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Classe Listener: ha la responsabilità di registrare log al verificarsi
 * di detetrminati eventi. Fa parte dell'implementazione del design pattern Observer
 */
@Service
public class LogNotifier implements EventListener {

    /**
     * Definisce il comportamento della classe al verificarsi di un
     * evento propagato dall'EventManager
     * @param eventType tipologia di evento
     * @param data dati utili alla gestione dell'evento
     */
    public void update(String eventType, Map<String, Object> data){
        System.out.println("Log dell'evento " + eventType);
    }

}
