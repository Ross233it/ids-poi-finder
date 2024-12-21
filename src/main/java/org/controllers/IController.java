package org.controllers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

/**
 * Le classi che implementano questa interfaccia assumono il ruolo di Controller
 * che nel pattern Mvc ha la responsabilità di gestire le interazioni fra il sistema
 * e l'interfaccia utente.
 * @param <O>
 */
public interface IController<O> extends HttpHandler {

/**
 * Questo metodo si occupa di gestire le richieste http in arrivo
 * e di fornire una risposta al chiamante.
 * @param exchange
 * @throws IOException
 */
   public void handle(HttpExchange exchange) throws IOException;
   public void index()  throws IOException;
   public void create() throws IOException;
   public void update() throws IOException;
   public void delete() throws IOException;

}
