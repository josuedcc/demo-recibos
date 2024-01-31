package com.demo.recibos.demorecibos.model;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Entity
@Table(name = "recibos_x_suministro", schema = "recibos_bd")
public class Recibo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "suministro", nullable = false, length = 8)
    private String suministro;

    @Column(name = "monto", nullable = false)
    private Double monto;

    @Column(name = "fecha_emision")
    private Timestamp fechaEmision;

}