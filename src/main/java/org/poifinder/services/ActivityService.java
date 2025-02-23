package org.poifinder.services;

import jakarta.persistence.EntityNotFoundException;
import org.poifinder.dataMappers.activities.ActivityListMapper;
import org.poifinder.httpServer.auth.UserContext;
import org.poifinder.models.activities.Activity;
import org.poifinder.models.activities.Contest;
import org.poifinder.models.activities.Prize;
import org.poifinder.models.activities.Rule;
import org.poifinder.models.municipalities.Municipality;
import org.poifinder.models.poi.Poi;
import org.poifinder.models.users.RegisteredUser;
import org.poifinder.repositories.PoiRepository;
import org.poifinder.repositories.activities.ActivityRepository;

import org.poifinder.repositories.MunicipalityRepository;
import org.poifinder.repositories.activities.PrizeRepository;
import org.poifinder.repositories.activities.RuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ActivityService extends BaseService<Activity> {

    @Autowired
    ActivityRepository activityRepository;
    @Autowired
    private MunicipalityRepository municipalityRepository;
    @Autowired
    private MunicipalityService municipalityService;
    @Autowired
    private RuleRepository ruleRepository;
    @Autowired
    private PrizeRepository prizeRepository;
    @Autowired
    private PoiRepository poiRepository;

    @Autowired
    public ActivityService(ActivityRepository repository,
                           ActivityListMapper mapper) {
            super(repository, mapper);
    }


    @Override
    public List<Activity> filter(Map<String, String> queryParams) throws Exception {
        return List.of();
    }

    @Override
    public Activity setStatus(Long id, String status) throws Exception {
        return null;
    }

    /**
     * Gestisce la creazione di una nuova attività in una qualsiasi delle
     * sue sottovarianti - experience -itinerary etc.
     * @param activity l'ggetto di classe attività da creare
     * @return activity  l'attività creata
     * @throws Exception
     */
    @Override
    public Activity create(Activity activity) throws Exception{

        activity = (Activity) publishOrPending(activity);
        RegisteredUser author = UserContext.getCurrentUser();
        Municipality municipality = municipalityService.getObjectById(activity.getMunicipality_id());

        activity.setMunicipality(municipality);
        activity.setAuthor(author);
        activity = (Activity) publishOrPending(activity);

        if (author == null || municipality == null) {
            throw new RuntimeException();
        }

        if (activity.getMunicipality().getId() == activity.getAuthor().getMunicipality().getId()) {
            if(activity.getType().equals("contest"))
                this.saveRulesAndPprizes(activity);
            Activity savedActivity = activityRepository.save(activity);
            this.notify(savedActivity);
            return savedActivity;
        }
        return null;
    }

    @Override
    public Activity getObjectById(Long id) {
        return  activityRepository.findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException("Activity con ID " + id + " non trovata")
                );
    }


    /**
     * Associa una serie di poi ad una attività
     * @param id l'id dell'attività a cui aggiungere i poi
     * @param poiIds gli id dei poi da associare
     * @return activity l'attività con i poi associati
     */
    public Activity addPois(Long id, List<Long> poiIds){
        if(poiIds == null || poiIds.isEmpty() || id == null)
            throw new IllegalArgumentException("L'id dell'attività o dei poi non sono corretti");

        RegisteredUser currentUser = UserContext.getCurrentUser();
        Activity activity = activityRepository.findById(id)
                                               .orElseThrow(
                   () -> new EntityNotFoundException("Activity con ID " + id + " non trovata")
               );

        if(activity.getAuthor().getId().equals(currentUser.getId())){
            List<Poi> pois    = poiRepository.findAllById(poiIds);
                if (pois.size() != poiIds.size()) {
                throw new EntityNotFoundException("Alcuni POI non sono stati trovati");
            }
            activity.setPoiList(pois);
        return  activityRepository.save(activity);
        }
        throw new IllegalArgumentException("Utente non autorizzato all'operazione");
    }


    /**
     * Riassegna tutti le attività che un utente ha pubblicato o approvato ad un altro
     * autore - responsabile
     * @param currentUser l'attuale autore - responsabile del activity
     * @param newUser  l'utente a cui il activity viene riassegnato
     */
    public void setAuthorAndApproverMassively(RegisteredUser currentUser, RegisteredUser newUser){
        List<Activity> activitiesAsAuthor = activityRepository.findByAuthor(currentUser);
        for (Activity activity : activitiesAsAuthor) {
            activity.setAuthor(newUser);
        }
        List<Activity> activitiesAsApprover = activityRepository.findByApprover(currentUser);
        for (Activity activity : activitiesAsApprover) {
            activity.setApprover(newUser);
        }
        activityRepository.saveAll(activitiesAsAuthor);
        activityRepository.saveAll(activitiesAsApprover);
    }

    /**
     * Assegna i premi e le regole ad un contest e le salva nello
     * srato di persistenza.
     * @param contest
     */
    private void saveRulesAndPprizes(Activity contest){
        RegisteredUser author = UserContext.getCurrentUser();
        String status = contest.getStatus();
        for (Prize prize : ((Contest) contest).getPrizes()) {
            prize.setStatus(status);
            prize.setAuthor(author);
            prizeRepository.save(prize);
        }
        for(Rule rule : ((Contest) contest).getRules()){
            rule.setAuthor(author);
            rule.setStatus(status);
            ruleRepository.save(rule);
        }
    }
}
