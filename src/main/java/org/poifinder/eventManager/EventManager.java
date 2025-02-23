package org.poifinder.eventManager;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Gestisce notifiche ed eventi per una lista di elementi in ascolto.
 * Fa parte dell'implementazione del design pattern Observer
 */
@Component
public class EventManager {

    List<EventListener> listeners;

    public EventManager(){
        this.listeners = new ArrayList<EventListener>();
    }

    /**
     * Aggiunge un elemento in ascolto
     * @param listener l'elemento che riceverà notifiche ed eventi
     */
    public void subscribe(EventListener listener){
        this.listeners.add(listener);
    }

    /**
     * Rimuove un elemento in ascolto
     * @param listener l'elemento da rimuovere
     */
    public void unsuscribe(EventListener listener){
        this.listeners.remove(listener);
    }

    /**
     * Notifica un evento a tutti gli elementi in asclto e
     * consente l'invio di informazioni per poterlo gestire
     * @param eventType String il tipo di evento
     * @param data Oggetti per la gestione dell'evento
     */
    public void notify(String eventType, Map<String, Object> data){
        for(EventListener listener : listeners)
            listener.update(eventType, data);
    }
}
