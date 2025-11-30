package com.Web.Plamilhas.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exceção customizada para quando um recurso (entidade ou arquivo)
 * não pode ser encontrado. Mapeia para um erro 404.
 */
@ResponseStatus(HttpStatus.NOT_FOUND) // Garante que o Spring retorne 404
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}