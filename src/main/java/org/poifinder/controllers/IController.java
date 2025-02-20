package org.poifinder.controllers;

import java.io.IOException;

/**
 * Le classi che implementano questa interfaccia assumono il ruolo di BaseController
 * che nel pattern Mvc ha la responsabilità di gestire le interazioni fra il sistema
 * e l'interfaccia utente.
 * @param <O>
 */
public interface IController<O>{
   void index()         throws IOException;
   void show()          throws IOException;
   void create()        throws IOException;
   void update()        throws IOException;
   void delete()        throws IOException;
}
