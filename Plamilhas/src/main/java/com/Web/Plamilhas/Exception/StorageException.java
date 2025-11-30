package com.Web.Plamilhas.Exception;

/**
 * Exceção customizada para encapsular erros de armazenamento
 * de arquivos (upload, download, deleção).
 */
public class StorageException extends RuntimeException {

    public StorageException(String message) {
        super(message);
    }

    public StorageException(String message, Throwable cause) {
        super(message, cause);
    }
}