package com.example.superheroes.controller;

import com.example.superheroes.exception.NotFoundException;
import com.example.superheroes.model.Superhero;
import com.example.superheroes.repository.ISuperheroRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SuperheroController {
    private final ISuperheroRepository _repository;

    public SuperheroController(ISuperheroRepository _repository) {
        this._repository = _repository;
    }

    @GetMapping("/get")
    public List<Superhero> get() {
        return _repository.findAll();
    }

    @GetMapping("/get/{id}")
    public Superhero getOne(@PathVariable Long id) {
        return _repository.findById(id)
                .orElseThrow(() -> new NotFoundException(id));
    }

    @PostMapping("/post")
    Superhero add(@RequestBody Superhero newSuperhero) {
        return _repository.save(newSuperhero);
    }

    @PutMapping("/put/{id}")
    Superhero replace(@RequestBody Superhero newSuper, @PathVariable Long id) {

        return _repository.findById(id)
                .map(superhero -> {
                    superhero.setName(newSuper.getName());
                    return _repository.save(superhero);
                })
                .orElseGet(() -> {
                    newSuper.setId(id);
                    return _repository.save(newSuper);
                });
    }

    @DeleteMapping("/delete/{id}")
    void delete(@PathVariable Long id) {
        _repository.deleteById(id);
    }
}
