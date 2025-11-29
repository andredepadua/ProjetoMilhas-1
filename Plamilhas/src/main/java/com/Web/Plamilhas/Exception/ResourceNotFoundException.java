package com.Web.Plamilhas.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND) // Mapeia esta exceção para o status 404
public class ResourceNotFoundException extends RuntimeException {

    // Construtor que aceita uma mensagem de erro
    public ResourceNotFoundException(String message) {
        super(message);
    }
}