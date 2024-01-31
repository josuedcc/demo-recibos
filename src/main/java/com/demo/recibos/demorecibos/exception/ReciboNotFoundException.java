package com.demo.recibos.demorecibos.exception;

public class ReciboNotFoundException extends RuntimeException {
    public ReciboNotFoundException(String suministro) {
        super("No se encontraron recibos para: " + suministro);
    }
}