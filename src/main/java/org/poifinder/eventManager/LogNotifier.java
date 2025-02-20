package org.poifinder.eventManager;

import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class LogNotifier implements EventListener {

    public void update(String eventType, Map<String, Object> data){
        System.out.println("Log dell'evento " + eventType);
    }

}
