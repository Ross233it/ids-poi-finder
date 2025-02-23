package org.poifinder.eventManager;

import org.poifinder.httpServer.auth.UserContext;
import org.poifinder.models.Content;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Classe Listener: ha la responsabilità di inviare notifiche email al verificarsi
 * di detetrminati eventi. Fa parte dell'implementazione del design pattern Observer
 */
@Service
public class EmailNotifier implements  EventListener{

    /**
     * Definisce il comportamento della classe al verificarsi di un
     * evento propagato dall'EventManager
     * @param eventType tipologia di evento
     * @param data dati utili alla gestione dell'evento
     */
    public void update(String eventType, Map<String, Object> data){
        if(eventType == "content-report"){
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
     * @param data le informazioni necessarie per gestire la segnalazione
     */
    private void contentReport(Map<String, Object> data )throws RuntimeException{
        if(data == null)
            throw new IllegalArgumentException();
        String emailAddress = (String) data.get("emailAddress");
        String emailContent =  data.get("userData").toString();
        try{
            this.SendEmail(emailAddress, emailContent);
        }catch(Exception e) {
            throw new RuntimeException("Si è verificato un errore nella segnalazione contenuto");
        }
    }

    /**
     * Rappresenta il servizio non implementato di invio messaggio email
     * @param emailAddress l'indirizzo del destinatario
     * @param messageData il corpo del messaggio
     */
    private boolean SendEmail(String emailAddress, String messageData){
        System.out.println("SEGNALAZIONE CONTENUTO: " );
        System.out.println(emailAddress);
        System.out.println("CONTENUTO DEL MESSAGGIO: ");
        System.out.println(messageData);
        return true;
    }
}
