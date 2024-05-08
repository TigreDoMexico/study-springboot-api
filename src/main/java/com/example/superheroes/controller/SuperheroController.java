package com.example.superheroes.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SuperheroController {

    @GetMapping("/get")
    public String get() {
        return "My Superhero";
    }
}
