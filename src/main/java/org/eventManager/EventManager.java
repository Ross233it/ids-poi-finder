package org.eventManager;

import org.httpServer.http.HttpRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


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

    public void notify(String eventType, Map<String, Object> data){
        for(EventListener listener : listeners)
            listener.update(eventType, data);
    }
}
