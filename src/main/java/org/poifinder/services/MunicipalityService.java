package org.poifinder.services;

import com.fasterxml.jackson.annotation.JsonView;
import org.poifinder.dataMappers.Views;
import org.poifinder.httpServer.auth.AuthUtilities;
import org.poifinder.httpServer.auth.UserContext;
import org.poifinder.models.municipalities.Municipality;
import org.poifinder.models.users.RegisteredUser;
import org.poifinder.repositories.MunicipalityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Classe che fornisce la logica di business correlata ai Comuni
 */
@Service
public class MunicipalityService extends BaseService<Municipality> {

    @Autowired
    private MunicipalityRepository municipalityRepository;

    @Autowired
    public MunicipalityService(MunicipalityRepository repository) {
        super(repository);
    }

    /**
     * Ritorna i comuni presenti a sistema.
     * @return
     */
    @Override
    @JsonView(Views.Public.class)
    public List<Municipality> index() {
        RegisteredUser currentUser = UserContext.getCurrentUser();
        if(currentUser != null &&currentUser.hasOneRole(List.of("platformAdmin")))
            return municipalityRepository.findAll();
        else
            return municipalityRepository.findPublicMunicipality();
    }

    /**
     * Servizio di ricerca di un Comune in base al nome e/o
     * ad un termine di ricerca
     * @param municipality
     * @param search
     * @return
     */
    public List<Municipality> search(String municipality, String search) {
        if (municipality != null && search != null) {
            return municipalityRepository.searchByMunicipalityAndSearch(municipality, search);
        } else if (municipality != null) {
            return municipalityRepository.searchByMunicipality(municipality);
        } else if (search != null) {
            return municipalityRepository.searchBySearch(search);
        } else {
            return repository.findAll();
        }
    }

    /**
     * Gestisce il servizio di creazione di un nuovo Comune
     * @param municipality l'oggetto da creare
     * @return object Municipality l'oggetto creato e salvato nello strato di persistenza.
     * @throws Exception
     */
    @Override
    public Municipality create(Municipality municipality) throws Exception {
        if (municipalityRepository.existsByName(municipality.getName()))
            throw new RuntimeException("Esiste già un comune con lo stesso nome");
        municipality.setAuthor(UserContext.getCurrentUser());
        municipality.setApprover(UserContext.getCurrentUser());
        try {
            return municipalityRepository.save(municipality);
        } catch (Exception e) {
            throw new RuntimeException("Si è verificato un errore durante il salvataggio: " + e.getMessage());
        }
    }

    @Override
    public Municipality getObjectById(Long id){
        RegisteredUser currentUser = UserContext.getCurrentUser();
        if(AuthUtilities.canUserHandleMunicipality(id)
            && currentUser.hasOneRole(List.of("platformAdmin", "animator", "curator")))
            return municipalityRepository.getById(id);
        else
            return municipalityRepository.findByIdWithPublishedPois(id);
    }

    @Override
    public Municipality update(Long id, Municipality municipality) throws Exception {
            municipality.setId(id);
            Municipality editedMunicipality = municipalityRepository.save(municipality);
        return editedMunicipality;
    }
}


