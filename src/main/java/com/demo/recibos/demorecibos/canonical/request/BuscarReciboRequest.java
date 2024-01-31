package com.demo.recibos.demorecibos.canonical.request;

import jakarta.validation.constraints.Pattern;
import lombok.Data;
import jakarta.validation.constraints.Size;

@Data
public class BuscarReciboRequest {

    @Size(min = 8, max = 8, message = "Numero de suministro debe ser de 8 caracteres")
    @Pattern(regexp = "\\d+", message = "Numero de suministro debe contener solo numeros")
    private String numeroDeSuministro;

}