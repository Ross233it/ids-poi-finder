package org.poifinder.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;


/**
 * Le classi che implementano questa interfaccia assumono il ruolo di Controller
 * che nel pattern MVC ha la responsabilità di gestire le interazioni fra il sistema
 * e le View - l'interfaccia utente.
 * @param <T>
 */
public interface IController<T>{

   ResponseEntity index() throws Exception;

   ResponseEntity show(@PathVariable Long id) throws Exception;

   ResponseEntity create(@RequestBody T entity) throws Exception;

   ResponseEntity update(@PathVariable Long id, @RequestBody T entity) throws Exception;

   ResponseEntity delete(@PathVariable Long id) throws Exception;

}
