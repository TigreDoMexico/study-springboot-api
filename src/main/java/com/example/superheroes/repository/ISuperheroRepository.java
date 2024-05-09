package com.example.superheroes.repository;

import com.example.superheroes.model.Superhero;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ISuperheroRepository extends JpaRepository<Superhero, Long> { }
