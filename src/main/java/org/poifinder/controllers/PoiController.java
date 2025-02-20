package org.poifinder.controllers;

import org.poifinder.models.municipalities.Municipality;
import org.poifinder.models.poi.Poi;
import org.poifinder.services.PoiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("api/poi")
public class PoiController extends BaseController<Poi, PoiService> {

    @Autowired
    public  PoiController(PoiService service) {
        super(service);
    }



    @GetMapping("/api/municipality/{id}")
    public Poi showTest(@PathVariable long id){
        try {
            return service.getObjectById(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @GetMapping("/api/poi/")
    public void create() throws IOException {
//        Map<String, Object> data = request.getBodyStreamData();
//        RegisteredUser author = UserContext.getCurrentUser();
//        data.put("author", author);
//        if(
//            author.hasRole("platformAdmin") ||
//            author.hasRole("animator") ||
//            author.hasRole("authContributor")
//        ){
//            data.put("status", "published");
//        }
//        handleRequest(()->service.create(data), null);
    }

    /**
     * Gestisce la richiesta di tutti i poi di un municipio
     * @throws IOException
     */


    public void getByMunicipalityId() throws IOException {
//        long municipalityId = request.getRequestId();
//        handleRequest(()-> service.getByMunicipalityId(municipalityId), null);
    }


    public void validate(){

    }


}
