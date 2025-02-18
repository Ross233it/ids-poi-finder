package org.eventManager;

import org.httpServer.http.HttpRequest;

import java.util.Map;

public class LogNotifier implements EventListener {

    public void update(String eventType, Map<String, Object> data){
        System.out.println("Log dell'evento " + eventType);
    }

}
