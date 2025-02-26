package org.poifinder.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import org.poifinder.dataMappers.Views;
import org.poifinder.models.users.FavoriteContents;

import org.poifinder.services.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/favorites")
public class FavoriteController extends BaseController<FavoriteContents>{


    @Autowired
    public FavoriteController(FavoriteService service) {
        super(service);
    }


    /**
     * Gestisce una richiesta di aggiunta di un contenuto poi
     * alla lista dei preferiti
     * @param id l'id del poi da aggiungere
     */
    @GetMapping("/{id}/handle")
    @JsonView(Views.Public.class)
    public FavoriteContents handleFavorites(@PathVariable Long id,
                                            @RequestParam String type,
                                            @RequestParam String operation){
        try {
            return ((FavoriteService) service).handleFavorites(id, type, operation);
        } catch (Exception e) {
            throw new RuntimeException("Errore durante la modifica dell'entità", e);
        }
    }
}
