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
 * @param <O>
 */
public interface IController<O>{

   ResponseEntity<List<O>> index(@RequestParam String queryString)         throws IOException;

   ResponseEntity<O> show(@PathVariable Long id) throws IOException;

   ResponseEntity<O> create(@RequestBody DataMapper<O> entityData) throws IOException;

   ResponseEntity<O> update(@PathVariable Long id, @RequestBody DataMapper<O> entityData) throws IOException;

   ResponseEntity<Void> delete(@PathVariable Long id) throws IOException;

}
