package org.poifinder.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import org.poifinder.dataMappers.Views;
import org.poifinder.models.activities.Activity;
import org.poifinder.models.activities.Contest;
import org.poifinder.models.activities.Experience;
import org.poifinder.models.activities.Itinerary;
import org.poifinder.services.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/activity")
public class ActivityController extends BaseController<Activity> {

    private final ActivityService activityService;

    @Autowired
    public  ActivityController(ActivityService service, ActivityService activityService) {
        super(service);
        this.activityService = activityService;
    }


    public Activity askForContest(){
        return null;
    }


    /**
     * Gestisce la richiesta di visualizzazione di una specifica risorsa.
     * @throws IOException
     */
    @Override
    @GetMapping("/{id}")
    @JsonView(Views.Public.class)
    public  ResponseEntity show(@PathVariable Long id) throws IOException {
        return super.show(id);
    }

    /**
     * Gestisce le chiamate di creazione di tutte le tipologie di
     * activity.
     * @param activity l'attività da creare
     * @return ResponseEntity la risposta alla chiamata.
     */
    @Override
    public ResponseEntity create(Activity activity){
        try {
            Activity createdActivity = service.create(activity);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdActivity);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Impossibile creare l'attività " + e);
        }
    }

    /**
     * Gestisce la chiamata a creazione di una esperienza.
     * @param experience
     * @param experience l'attività da creare
     * @return ResponseEntity la risposta alla chiamata.
     */
    @PostMapping ("experience")
    @JsonView(Views.Public.class)
    public ResponseEntity createExperience(@RequestBody Experience experience) throws IOException {
        experience.setType("experience");
        return this.create(experience);
    }

    /**
     * Gestisce la chiamata a creazione di un itinerario.
     * @param itinerary
     * @param itinerary l'attività da creare
     * @return ResponseEntity la risposta alla chiamata.
     */
    @PostMapping ("itinerary")
    @JsonView(Views.Public.class)
    public ResponseEntity createItinerary(@RequestBody Itinerary itinerary) throws IOException {
        itinerary.setType("itinerary");
        return this.create(itinerary);
    }

    /**
     * Gestisce la chiamata a creazione di un itinerario.
     * @param contest
     * @param contest l'attività da creare
     * @return ResponseEntity la risposta alla chiamata.
     */
    @PostMapping ("contest")
    @JsonView(Views.Public.class)
    public ResponseEntity createContest(@RequestBody Contest contest) throws IOException {
        contest.setType("contest");
        return this.create(contest);
    }


    @PostMapping("/{id}/add")
    @JsonView(Views.Public.class)
    public ResponseEntity addPois(@PathVariable Long id, @RequestBody List<Long> poi_ids) throws IOException {
        try {
            Activity editedActivity = activityService.addPois(id, poi_ids);
            return ResponseEntity.status(HttpStatus.CREATED).body("Poi correttamente associati a " + editedActivity);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Impossibile associare i Poi all'attivita " + e);
        }
    }

    public void deleteActivity(){

    }
}
