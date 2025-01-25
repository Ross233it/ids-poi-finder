package org.models;

import org.models.users.RegisteredUser;

/**
 * Astrae il concetto di contenuto, inteso come dato o informazione
 * consultabile all'interno del sistema inserito da un autore ed approvato da un responsabile.
 */
public abstract  class Content implements IModel {
    private long id;

    private RegisteredUser author;

    private RegisteredUser approver;
    /**
     * Restituisce l'id del modello
     * @return long id
     */
    @Override
    public long getId() { return id; }

    public RegisteredUser getAuthor() { return author; }

    public RegisteredUser getApprover() { return approver; }

    /**
     * Setta l'id del modello
     * @param id long id da impostare
     */
    @Override
    public void setId(long id) { this.id = id; }

    public void setAuthor(RegisteredUser author) { this.author = author; }

    public void setApprover(RegisteredUser approver) { this.approver = approver; }

    /**
     * Restituisce i dati dell'oggetto
     * @return Object[] array di oggetti
     */
    @Override
    public abstract Object[] getData();

    /**
     * Restituisce una rappresentazione testuale dell'oggetto
     * @return
     */
    @Override
    public abstract String toString();
}
