package com.demo.recibos.demorecibos.controller;

import com.demo.recibos.demorecibos.canonical.request.BuscarReciboRequest;
import com.demo.recibos.demorecibos.model.Recibo;
import com.demo.recibos.demorecibos.services.ReciboService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class RecibosController {

    @Autowired
    ReciboService reciboService;

    @PostMapping("/buscarPorSuministro")
    public ResponseEntity<Recibo> buscarPorSuministro(@Valid @RequestBody BuscarReciboRequest buscarReciboRequest) {

        return ResponseEntity.ok(reciboService.buscarPorSuministro(buscarReciboRequest.getNumeroDeSuministro()));
    }
}