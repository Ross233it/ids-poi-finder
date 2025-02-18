package org.eventManager;



import java.util.Map;

public interface EventListener {

    public void update(String eventType, Map<String, Object> data);

}
