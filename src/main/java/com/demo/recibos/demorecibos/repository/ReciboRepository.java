package com.demo.recibos.demorecibos.repository;

import com.demo.recibos.demorecibos.model.Recibo;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReciboRepository extends JpaRepository<Recibo,Integer> {

    List<Recibo> findBySuministroOrderByFechaEmisionDesc(String suministro, Pageable pageable);

}