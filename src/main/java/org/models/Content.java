package org.models;

/**
 * Astrae il concetto di contenuto, inteso come dato o informazione
 * consultabile all'interno del sistema.
 */
public abstract  class Content implements IModel {
    private long id;
    /**
     * Restituisce l'id del modello
     * @return long id
     */
    @Override
    public long getId() { return id; }

    /**
     * Setta l'id del modello
     * @param id long id da impostare
     */
    @Override
    public void setId(long id) { this.id = id; }

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
