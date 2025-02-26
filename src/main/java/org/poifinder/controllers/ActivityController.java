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

    @Autowired
    private ActivityService activityService;

    @Autowired
    public  ActivityController(ActivityService service) {
        super(service);
    }


    /**
     * Gestisce le richieste di creazione di una delle attività.
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
     * Gestisce le richieste di creazione di una attività esperienza.
     * @param experience le informazioni relative alla nuova esperienza
     * @return ResponseEntity la risposta http alla richiesta
     */
    @PostMapping ("experience")
    @JsonView(Views.Public.class)
    public ResponseEntity createExperience(@RequestBody Experience experience) throws IOException {
        experience.setType("experience");
        return this.create(experience);
    }

    /**
     * Gestisce le richieste di creazione di una attività itinerario.
     * @param itinerary le informazioni relative al nuovo itinerario
     * @return ResponseEntity la risposta http alla richiesta
     */
    @PostMapping ("itinerary")
    @JsonView(Views.Public.class)
    public ResponseEntity createItinerary(@RequestBody Itinerary itinerary) throws IOException {
        itinerary.setType("itinerary");
        return this.create(itinerary);
    }

    /**
     * Gestisce le richieste di creazione di una attività contest.
     * @param contest le informazioni relative al nuovo contest
     * @return ResponseEntity la risposta http alla richiesta.
     */
    @PostMapping ("contest")
    @JsonView(Views.Public.class)
    public ResponseEntity createContest(@RequestBody Contest contest) throws IOException {
        contest.setType("contest");
        return this.create(contest);
    }

    /**
     * Gestisce le richieste di associazione di una attività ad
     * una serie di Poi
     * @param id l'id dell'attività
     * @param poi_ids l'id dei poi da associare
     * @return ResponseEntity responseHTTP
     * @throws IOException
     */
    @PostMapping("/{id}/add")
    @JsonView(Views.Public.class)
    public ResponseEntity addPois(@PathVariable Long id, @RequestBody List<Long> poi_ids) throws IOException {
        try {
            Activity editedActivity = activityService.addPois(id, poi_ids);
            if(editedActivity.getType().equals("contest"))
                editedActivity = (Contest) editedActivity;
            return ResponseEntity.status(HttpStatus.CREATED).body("Poi correttamente associati a " + editedActivity);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Impossibile associare i Poi all'attivita " + e);
        }
    }
}
