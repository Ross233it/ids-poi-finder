package org.eventManager;

import org.httpServer.auth.UserContext;

public class EmailNotifier implements  EventListener{


    public void update(String eventType){
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
}
