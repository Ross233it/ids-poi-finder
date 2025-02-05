package org.services;

import org.controllers.Controller;
import org.dataMappers.MunicipalityMapper;
import org.models.municipalities.Municipality;
import org.models.GeoLocation;
import org.models.municipalities.MunicipalityBuilder;
import org.models.poi.Poi;
import org.models.users.RegisteredUser;
import org.repositories.GeoLocationRepository;
import org.repositories.MunicipalityRepository;
import org.repositories.PoiRepository;
import org.repositories.RegisteredUserRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class MunicipalityService extends Service<Municipality> {

    private GeoLocationService  geoLocationService;

    private RegisteredUserService  registeredUserService;

    private MunicipalityMapper municipalityMapper;

    public MunicipalityService(MunicipalityRepository repository) {
        super(repository);
        this.geoLocationService    = new GeoLocationService(new GeoLocationRepository());
        this.registeredUserService = new RegisteredUserService(new RegisteredUserRepository());
        this.municipalityMapper    = new MunicipalityMapper();
    }



    /**
     * Ritorna un Comune correadato della sua lista di Punti di interesse
     * @param id l'id del comune di interesse
     * @return Municipality municipality il comune con i suoi punti di interesse.
     * @throws Exception
     */
    public Municipality getWithPois(Long id) throws IOException,Exception {
        Municipality municipality = super.getObjectById(id);
        ArrayList<Poi> pois = new ArrayList<>();
        municipality.setPois(new PoiService(new PoiRepository("pois")).getByMunicipalityId(id));
        return municipality;
    }


    @Override
    protected Municipality buildEntity(Map<String, Object> objectData)throws Exception{
        GeoLocation geoLocation = this.geoLocationService.getOrCreate(objectData);

        RegisteredUser author = getAuthor(objectData);

        Municipality municipality = new MunicipalityBuilder(
                (String) objectData.get("name"),
                (String) objectData.get("region"),
                (String) objectData.get("province")
        ).geoLocation(geoLocation)
         .author(author)
         .build();

        municipality.setId((Long)objectData.get("id"));

        return municipality;
    }


    @Override
    protected Municipality buildEntityFromDb(Map<String, Object> objectData)throws Exception{
        GeoLocation geoLocation = this.geoLocationService.getOrCreate(objectData);

        RegisteredUser author = getAuthor(objectData);

        Municipality municipality = new MunicipalityBuilder(
                (String) objectData.get("name"),
                (String) objectData.get("region"),
                (String) objectData.get("province")
        ).geoLocation(geoLocation)
                .author(author)
                .build();

        municipality.setId((Long)objectData.get("id"));

        return municipality;
    }

    private RegisteredUser getAuthor(Map<String, Object> objectData) throws Exception {
        long authorId = 0;
        Object authorIdObj = objectData.get("author_id");

        if (authorIdObj instanceof Long) {
            authorId = (Long) authorIdObj;
        } else if (authorIdObj instanceof Integer) {
            authorId = ((Integer) authorIdObj).longValue();
        } else if (authorIdObj instanceof String) {
            authorId = Long.parseLong((String) authorIdObj);
        }

        if(authorId == 0)
             return  Controller.getAuthor();
         return registeredUserService.getObjectById(authorId);
    }
}
