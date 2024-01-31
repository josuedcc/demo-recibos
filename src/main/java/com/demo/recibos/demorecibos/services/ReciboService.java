package com.demo.recibos.demorecibos.services;

import com.demo.recibos.demorecibos.exception.ReciboNotFoundException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.demo.recibos.demorecibos.repository.ReciboRepository;
import com.demo.recibos.demorecibos.model.Recibo;

import java.util.List;

@Service
public class ReciboService {

    @Autowired
    ReciboRepository reciboRepository;

    @Autowired
    public ReciboService(ReciboRepository reciboRepository) {
        this.reciboRepository = reciboRepository;
    }

    public Recibo buscarPorSuministro(String suministro) {
        List<Recibo> recibos = reciboRepository.findBySuministroOrderByFechaEmisionDesc(suministro, PageRequest.of(0, 1));
        if (recibos.isEmpty()) {
            throw new ReciboNotFoundException(suministro);
        }
        return recibos.get(0);
    }

}