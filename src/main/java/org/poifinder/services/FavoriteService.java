package org.poifinder.services;

import org.poifinder.httpServer.auth.UserContext;
import org.poifinder.models.IModel;
import org.poifinder.models.activities.Activity;
import org.poifinder.models.poi.Poi;
import org.poifinder.models.users.FavoriteContents;
import org.poifinder.models.users.RegisteredUser;
import org.poifinder.repositories.FavoriteRespository;
import org.poifinder.repositories.PoiRepository;
import org.poifinder.repositories.activities.ActivityRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class FavoriteService extends BaseService<FavoriteContents>  {

    private final ActivityRepository activityRepository;
    private final PoiRepository poiRepository;
    private final FavoriteRespository favoriteRespository;

    public FavoriteService(FavoriteRespository repository, ActivityRepository activityRepository, PoiRepository poiRepository, FavoriteRespository favoriteRespository) {
        super(repository);
        this.activityRepository = activityRepository;
        this.poiRepository = poiRepository;
        this.favoriteRespository = favoriteRespository;
    }


    /**
     * Recupera la lista dei preferiti dell'utente correntemente
     * autenticato
     * @return List<FavoriteContents> la lista dei preferiti
     */
    @Override
    public List<FavoriteContents> index(){
        RegisteredUser currentUser = UserContext.getCurrentUser();
        return favoriteRespository.getByUserId(currentUser.getId());
    }


    /**
     * Aggiunge un contenuto alla lista dei preferiti
     * @param id l'id del contenuto da aggiungere
     * @param contentType il tipo di contenuto da aggiungere
     * @return il favoriteContent
     */

    public FavoriteContents handleFavorites(Long id, String contentType, String operationType){

        RegisteredUser currentUser = UserContext.getCurrentUser();

        if(currentUser == null)
            throw new RuntimeException("Aggiungi preferiti non consentito. Utente non autenticato");
        try{
            FavoriteContents favoriteContents = favoriteRespository.findByUserId(currentUser.getId());
            if(favoriteContents == null)
                favoriteContents = new FavoriteContents(currentUser);
            return this.addContent(id, contentType, favoriteContents, operationType);
        }catch (Exception e){
            throw new RuntimeException("Nessun contenuto preferito trovato per l'utente con ID: " + e);
        }
    }

    /**
     * Aggiugne un contenuto alla lista dei preferiti
     * @param id l'id del contenuto da aggiungere
     * @param contentType il tipo di contenuto da aggiungere
     * @param favoriteContent la lista dei contenuti da aggiungere
     * @param operation type remove or add
     * @return
     */
    private FavoriteContents addContent(Long id,
                                        String contentType,
                                        FavoriteContents favoriteContent,
                                        String operation){
        if(contentType.equals("activity")){
            Activity favoriteActivity;
                favoriteActivity = activityRepository.getById(id);
            if(operation.equals("add"))
                favoriteContent.addContent(favoriteActivity);
            else
                favoriteContent.removeContent(favoriteActivity);
        }
        else if(contentType.equals("poi")) {
            Poi favoritePoi;
            favoritePoi = poiRepository.getById(id);
            if(operation.equals("add"))
                favoriteContent.addContent(favoritePoi);
            else
                favoriteContent.removeContent(favoritePoi);

        }
        favoriteRespository.save(favoriteContent);
        return favoriteContent;
    }
}
