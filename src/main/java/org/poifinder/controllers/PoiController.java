package org.poifinder.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import org.poifinder.dataMappers.DataMapper;
import org.poifinder.dataMappers.Views;
import org.poifinder.dataMappers.poi.PoiCreateMapper;
import org.poifinder.dataMappers.poi.PoiListMapper;
import org.poifinder.models.municipalities.Municipality;
import org.poifinder.models.poi.Poi;
import org.poifinder.services.MunicipalityService;
import org.poifinder.services.PoiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/poi")
public class PoiController extends BaseController<Poi> {

@Autowired
private PoiService poiService;
    @Autowired
    private MunicipalityService municipalityService;


    @Autowired
public  PoiController(PoiService service) {
    super(service);
}

    @GetMapping("/search")
    @JsonView(Views.Public.class)
    public ResponseEntity<List<Poi>> searchPoi(
            @RequestParam(required = false) String municipality,
            @RequestParam(required = false) String search){
        try {
            List<Poi> result =  poiService.search(municipality, search);

            if (result.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * Gestisce la richiesta di tutti i poi di un municipio
     * @throws IOException
     */
    @GetMapping("/municipality/{id}")
    public void getByMunicipalityId(@PathVariable long id) throws IOException {
        try {
            poiService.getByMunicipalityId(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
