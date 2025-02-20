package org.poifinder.eventManager.JavaFirstVersion;



import java.util.Map;

public interface EventListener {

    public void update(String eventType, Map<String, Object> data);

}
