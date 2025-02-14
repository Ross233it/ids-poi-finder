package org.httpServer.auth;

import org.models.users.RegisteredUser;

/**
 * Gestisce il contesto dell'utente autenticato per ogni thread.
 * Utilizza ThreadLocal per mantenere i dati dell'utente attuale
 * in modo isolato per ogni richiesta.
 */
public class UserContext {
    /**
     * Crea il contesto per l'utente corrente
     */
    private static final ThreadLocal<RegisteredUser> context = new ThreadLocal<>();


    /**
     * Inserisce nel contesto l'utente
     * @param user l'utente autenticato
     */
    public static void setCurrentUser(RegisteredUser user){
        context.set(user);
    }

    /**
     * Recupera l'utente dal contesto.
     * @return RegisteredUser currentUser
     */
    public static RegisteredUser getCurrentUser(){
        return context.get();
    }

    /**
     * Pulisce il contesto rimuovendo gli oggetti
     * presenti nel thread.
     */
    public static void clear(){
        context.remove();
    }
}
