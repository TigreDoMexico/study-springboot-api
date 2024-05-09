package com.example.superheroes.controller;

import com.example.superheroes.exception.NotFoundException;
import com.example.superheroes.model.Superhero;
import com.example.superheroes.repository.ISuperheroRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.BDDMockito.given;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@WebMvcTest(controllers = SuperheroController.class)
@ExtendWith(MockitoExtension.class)
public class SuperheroControllerTests {
    @MockBean
    private ISuperheroRepository superheroRepositoryMock;

    @Autowired
    private SuperheroController superheroController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void When_GetAll_Should_ReturnAllSuperheroes() {
        List<Superhero> superheroes = new ArrayList<>();
        superheroes.add(new Superhero("Superman"));
        superheroes.add(new Superhero("Batman"));

        given(superheroRepositoryMock.findAll())
                .willAnswer(i -> superheroes);

        List<Superhero> result = superheroController.get();

        assertEquals(2, result.size());
        assertEquals("Superman", result.get(0).getName());
        assertEquals("Batman", result.get(1).getName());
    }

    @Test
    public void Given_IdOfSuperheroThatExists_When_GetOne_Should_ReturnOneSuperhero() {
        Superhero superhero = new Superhero("Spider man");

        given(superheroRepositoryMock.findById(ArgumentMatchers.eq(1L)))
                .willAnswer(i -> Optional.of(superhero));

        Superhero result = superheroController.getOne(1L);

        assertEquals("Spider man", result.getName());
    }

    @Test
    public void Given_IdOfSuperheroThatNotExists_When_GetOne_Should_ThrowError() {
        given(superheroRepositoryMock.findById(ArgumentMatchers.eq(1L)))
                .willAnswer(i -> Optional.empty());

        assertThrows(NotFoundException.class, () -> superheroController.getOne(1L));
    }

    @Test
    public void Given_SuperheroToSave_When_Add_ShouldAddToRepository() {
        Superhero newSuperhero = new Superhero("Iron Man");

        given(superheroRepositoryMock.save(newSuperhero)).willAnswer(i -> newSuperhero);

        Superhero result = superheroController.add(newSuperhero);

        verify(superheroRepositoryMock, times(1)).save(newSuperhero);
        assertEquals("Iron Man", result.getName());
    }

    @Test
    public void Given_SuperheroToUpdate_When_Update_ShouldReplaceInRepository() {
        Superhero existingSuperhero = new Superhero("Superman");
        Superhero updatedSuperhero = new Superhero("Batman");

        given(superheroRepositoryMock.findById(1L))
                .willAnswer(i -> Optional.of(existingSuperhero));
        given(superheroRepositoryMock.save(existingSuperhero))
                .willAnswer(i -> updatedSuperhero);

        Superhero result = superheroController.replace(updatedSuperhero, 1L);

        verify(superheroRepositoryMock, times(1)).findById(1L);
        verify(superheroRepositoryMock, times(1)).save(existingSuperhero);
        assertEquals("Batman", result.getName());
    }

    @Test
    public void testDeleteSuperhero() {
        superheroController.delete(1L);
        verify(superheroRepositoryMock, times(1)).deleteById(1L);
    }
}
