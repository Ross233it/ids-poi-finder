package org.poifinder.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import org.poifinder.dataMappers.Views;
import org.poifinder.models.municipalities.Municipality;
import org.poifinder.services.MunicipalityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/municipality")
public class MunicipalityController extends BaseController<Municipality> {


    public  MunicipalityController(MunicipalityService service) {
        super(service);
    }

    @GetMapping("/search")
    @JsonView(Views.Public.class)
    public ResponseEntity<List<Municipality>> searchMunicipality(
            @RequestParam(required = false) String municipality,
            @RequestParam(required = false) String search){
        try {
            List<Municipality> result =  ((MunicipalityService) service).search(municipality, search);
            if (result.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}

