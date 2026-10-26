package com.vlascares.inventario.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String resource, Long id) {
        super(String.format("%s con id %d no existe", resource, id));
    }
}
