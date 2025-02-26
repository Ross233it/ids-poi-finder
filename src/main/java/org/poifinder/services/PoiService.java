package org.poifinder.services;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.EntityNotFoundException;
import org.poifinder.dataMappers.Views;
import org.poifinder.httpServer.auth.UserContext;
import org.poifinder.models.municipalities.Municipality;
import org.poifinder.models.GeoLocation;
import org.poifinder.models.poi.Poi;
import org.poifinder.models.users.RegisteredUser;
import org.poifinder.repositories.PoiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Ha la responsabilità di gestire la logica di business connessa alla
 * manipolazione ed all'interazione con gli oggetti di tipo POI.
 */
@Service
@Primary
public class PoiService extends BaseService<Poi> {


    @Autowired
    private MunicipalityService municipalityService;

    @Autowired
    private PoiRepository poiRepository;

    @Autowired
    public PoiService(PoiRepository repository) {
        super(repository);
    }


    /**
     * Ritorna i Poi presenti a sistema differenziando i pubblici
     * dai pending
     * @return List<Poi> i risultati della ricerca
     */
    @Override
    @JsonView(Views.Public.class)
    public List<Poi> index() {
        RegisteredUser currentUser = UserContext.getCurrentUser();
        if(currentUser != null &&currentUser.hasRole("platformAdmin"))
            return poiRepository.findAll();
        else
            return poiRepository.findByStatus("published");
    }


    /**
     * Ritorna un poi in base alla sua visibilità
     * @param id l'id del poi da visualizzare
     * @return il poi ritrovato
     * @throws Exception
     */
    @Override
    public Poi getObjectById(Long id) throws Exception {
        Poi result = poiRepository.getById(id);
        if(result.getStatus().equals("pending")) {
            RegisteredUser currentUser = UserContext.getCurrentUser();
            if (currentUser == null)
                throw new RuntimeException("Visualizzazione non consentita");
        }
        return result;
    }
    /**
     * Servizio di ricerca di poi in base al comune e/o ad un termine di ricerca
     * @param municipality
     * @param search
     * @return
     */
    public List<Poi> search(String municipality, String search) {
        if (municipality != null && search != null) {
            return poiRepository.searchByMunicipalityAndSearch(municipality, search);
        } else if (municipality != null) {
            return poiRepository.searchByMunicipality(municipality);
        } else if (search != null) {
            return poiRepository.searchBySearch(search);
        } else {
            return repository.findAll();
        }
    }

    /**
     * Recupera un oggetto Poi in base all'id e ne memorizza lo stato
     * @param id identificativo dell'entità
     * @param status lo stato da memorizzare per l'entità
     * @return poi l'oggetto con lo stato settato.
     * @throws Exception
     */
    @Override
    public Poi setStatus(Long id, String status) throws Exception {
        Poi poi = poiRepository.getObjectById(id);
        if(poi != null && poi.getStatus().equals("pending")){
            RegisteredUser approver = UserContext.getCurrentUser();
            if(approver == null)
                throw new RuntimeException("Impossibile validare il contenuto: Approver non identificato");
            //approvazione solo dei comuni di propria competenza
            if(approver.getMunicipality().getId() == poi.getMunicipality().getId()){
                if(status.equals("rejected"))
                    this.delete(id);
                poi.getGeoLocation().setStatus(status);
                poi.setApprover(approver);
                poi.setStatus(status);
                return poiRepository.save(poi);
            }else
                throw new RuntimeException("Errore durante la validazione dell'entità: assicurati di avere i permessi necessari");
        }
        return null;
    }

    /**
     * Gestisce il servizio di creazione di una nuova entità a database
     * @param poi informazioni dell'oggetto da creare
     * @return object Municipality l'oggetto creato e salvato nello strato di persistenza.
     * @throws Exception
     */
    @Override
    public Poi create(Poi poi) throws Exception {
        RegisteredUser author = UserContext.getCurrentUser();
        Municipality municipality = municipalityService.getObjectById(poi.getMunicipality_id());

        if (author == null || municipality == null) {
            throw new RuntimeException();
        }

        if (poi.getIsLogical()) {
            GeoLocation geolocation = municipality.getGeoLocation();
            poi.setGeoLocation(geolocation);
        }

        poi.setMunicipality(municipality);
        poi.setAuthor(author);
        poi = (Poi) publishOrPending(poi);

            Poi newPoi;
            try {
                newPoi = poiRepository.save(poi);
            }catch (Exception e){
                System.out.println(e);
                throw new Exception("Si è verificato un problema durante il salvataggio: " + e.getMessage(), e);
            }
            if(newPoi != null){
                this.notify(newPoi);
                return newPoi;
            }
        throw new Exception("Impossibile salvare il POI: verifica i tuoi permessi per il comune proposto");
    }

    /**
     * Riassegna tutti i poi che un utente ha pubblicato o approvato ad un altro
     * autore - responsabile
     * @param currentUser l'attuale autore - responsabile del poi
     * @param newUser  l'utente a cui il poi viene riassegnato
     */
    public void setAuthorAndApproverMassively(RegisteredUser currentUser, RegisteredUser newUser){
        List<Poi> poisAsAuthor = poiRepository.findByAuthor(currentUser);
        for (Poi poi : poisAsAuthor) {
            poi.setAuthor(newUser);
        }
        List<Poi> poisAsApprover = poiRepository.findByApprover(currentUser);
                for (Poi poi : poisAsApprover) {
            poi.setApprover(newUser);
        }
        poiRepository.saveAll(poisAsAuthor);
        poiRepository.saveAll(poisAsApprover);
    }


    /**
     * Aggiorna un Poi con nuove informazioni
     * @param id l'id del poi da aggiornare
     * @param poi il poi di cui aggiornare le informazioni
     * @return poi il poi aggiornato
     * @throws Exception
     */
    @Override
    public Poi update(Long id, Poi poi) throws Exception {
        RegisteredUser currentUser = UserContext.getCurrentUser();
        Poi oldPoi = poiRepository.getObjectById(id);
        if(currentUser == null)
            throw new IllegalArgumentException("Non sei autenticato");
        if(oldPoi.getAuthor().getId() != currentUser.getId() || !currentUser.getRole().equals("platformAdmin"))
            throw new IllegalArgumentException("Author Exception: Non puoi modificare questo punto di interesse");
        poi.setMunicipality(oldPoi.getMunicipality());
        poi.setAuthor(currentUser);
        poi.setStatus("pending");
        poi.setApprover(null);
        poi = (Poi) publishOrPending(poi);
        this.notify(poi);
        try {
            return poiRepository.save(poi);
        }catch (Exception e){
            System.out.println(e);
            throw new Exception("Si è verificato un problema durante l'aggiornamento: " + e.getMessage(), e);
        }
    }



    /**
     * Rimuove il poi senza interferire con i dati del comune
     * @param id l'identificativo del Poi da rimuovere
     * @return il poi eliminato
     * @throws Exception
     */
    @Override
    public Poi delete(Long id) throws Exception {
        Poi toDelete = poiRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("POI con ID " + id + " non trovato"));
        if((toDelete.getGeoLocation().getId()).equals(toDelete.getMunicipality().getGeoLocation().getId()))
            toDelete.setGeoLocation(null);
        toDelete.setMunicipality(null);
        toDelete.setAuthor(null);
        toDelete.setApprover(null);
        poiRepository.delete(toDelete);
        return toDelete;
    }

    public List<Poi> getByMunicipalityId(long id) throws Exception {
        return poiRepository.getByMunicipalityId(id);
    }
}