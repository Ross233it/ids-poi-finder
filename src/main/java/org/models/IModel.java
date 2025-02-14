package org.models;

/**
 * Astrae il concetto di contenuto, identificata in modo univoco da un id
 * e che può essere rappresentata da un array di oggetti.
 */
public interface IModel {

    /**
     * Restituisce i dati dell'oggetto
     * @return Object[] array di oggetti
     */
    Object[] getData();

    /**
     * Restituisce l'identificativo univoco dell'informazione
     * @return id univoco
     **/
    long getId();

    /**
     * Setta l'identificativo univoco dell'informazione
     * @param id un valore numerico da assegnare
     */
    void setId(long id);



}
