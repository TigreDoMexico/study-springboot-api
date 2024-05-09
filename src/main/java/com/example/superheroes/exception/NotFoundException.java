package com.example.superheroes.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException(Long id) {
        super("Could not find data with Id: " + id);
    }
}
