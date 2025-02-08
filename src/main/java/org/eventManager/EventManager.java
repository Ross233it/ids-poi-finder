package org.eventManager;

import java.util.ArrayList;
import java.util.List;

public class EventManager {

    List<EventListener> listeners;

    public EventManager(){
        this.listeners = new ArrayList<EventListener>();
    }

    public void subscribe(EventListener listener){
        this.listeners.add(listener);
    }

    public void unsuscribe(EventListener listener){
        this.listeners.remove(listener);
    }

    public void notify(String eventType){
        for(EventListener listener : listeners)
            listener.update(eventType);
    }
}
