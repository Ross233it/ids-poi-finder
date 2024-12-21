package org.controllers;


import org.models.Municipality;
import org.services.MunicipalityService;

public class MunicipalityController extends Controller<Municipality> {

    public  MunicipalityController() {
        super(new MunicipalityService());   }
}

