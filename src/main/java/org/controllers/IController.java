package org.controllers;

import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

/**
 * Le classi che implementano questa interfaccia assumono il ruolo di Controller
 * che nel pattern Mvc ha la responsabilità di gestire le interazioni fra il sistema
 * e l'interfaccia utente.
 * @param <O>
 */
public interface IController<O> extends HttpHandler {
   void index()         throws IOException;
   void show(long id)   throws IOException;
   void create()        throws IOException;
   void update()        throws IOException;
   void delete(long id) throws IOException;
}
