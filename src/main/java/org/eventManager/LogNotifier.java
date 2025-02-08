package org.eventManager;

public class LogNotifier implements EventListener {

    public void update(String eventType){
        System.out.println("Log dell'evento " + eventType);
    }

}
