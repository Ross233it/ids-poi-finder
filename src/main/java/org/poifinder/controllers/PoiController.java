package org.poifinder.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import org.poifinder.dataMappers.Views;
import org.poifinder.models.poi.Poi;
import org.poifinder.services.PoiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/poi")
public class PoiController extends BaseController<Poi> {

    @Autowired
    private PoiService poiService;

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
}
