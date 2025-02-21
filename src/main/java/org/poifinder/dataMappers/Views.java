package org.poifinder.dataMappers;


/**
 * Classe che definisce le viste per la serializzazione dei dati
 */
public class Views {

    /** vista con livello di dettaglio più basso **/
    public static class Public {}

    /** vista con livello di dettaglio intermedio **/
    public static class Internal extends Public {}

    /** vista con livello di dettaglio più alto**/
    public static class Admin extends Internal {}
}
