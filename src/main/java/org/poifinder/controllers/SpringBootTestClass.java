package org.poifinder.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SpringBootTestClass {

    @GetMapping("/test")
    public String test(){
        return "Metodo di test raggiunto"; }

}
