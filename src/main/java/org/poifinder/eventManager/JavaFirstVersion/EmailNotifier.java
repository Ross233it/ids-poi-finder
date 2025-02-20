package org.poifinder.eventManager.JavaFirstVersion;


import org.poifinder.httpServer.auth.UserContext;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class EmailNotifier implements EventListener {


    public void update(String eventType, Map<String, Object> data){
        if(eventType == "content report"){
            this.contentReport(data);
        }
        else if(data == null)
            this.pendingCrudNotification(eventType);
    }


    /**
     * Invia una notifica email allo scatenarsi di un dato evento.
     * @param eventType
     */
    private void pendingCrudNotification(String eventType){
        System.out.println("NOTIFICA VIA EMAIL INVIATA AGLI INDIRIZZI");
        String authorEmail = UserContext.getCurrentUser().getEmail();
        System.out.println(authorEmail);
    }

    /**
     * Emula l'invio di una email stampando in console una serie di informazioni
     * ricevute come parametro.
     * @param data
     */
    private Map<String, Object> contentReport(Map<String, Object> data ){
        for (Map.Entry<String, Object> entry : data.entrySet()) {
            String key = entry.getKey();
            String value = String.valueOf(entry.getValue());
            System.out.println(key + ": " + value);
        }
        return data;
    }
}
