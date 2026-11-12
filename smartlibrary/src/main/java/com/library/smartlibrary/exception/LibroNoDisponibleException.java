package com.library.smartlibrary.exception;

public class LibroNoDisponibleException extends RuntimeException {
    public LibroNoDisponibleException(String mensaje) {
        super(mensaje);
    }
}
