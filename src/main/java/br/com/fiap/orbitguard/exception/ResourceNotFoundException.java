package br.com.fiap.orbitguard.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String resource, Long id) {
        super(resource + " nao encontrado com id " + id + ".");
    }
}
