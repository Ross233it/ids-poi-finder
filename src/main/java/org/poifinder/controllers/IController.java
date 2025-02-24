package org.poifinder.controllers;

import org.poifinder.dataMappers.DataMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.List;

/**
 * Le classi che implementano questa interfaccia assumono il ruolo di Controller
 * che nel pattern Mvc ha la responsabilità di gestire le interazioni fra il sistema
 * e l'interfaccia utente.
 * @param <T>
 */
public interface IController<T>{

   ResponseEntity index(@RequestParam String queryString) throws Exception;

   ResponseEntity show(@PathVariable Long id) throws Exception;

   ResponseEntity create(@RequestBody T entity) throws Exception;

   ResponseEntity update(@PathVariable Long id, @RequestBody T entity) throws Exception;

   ResponseEntity delete(@PathVariable Long id) throws Exception;

}
