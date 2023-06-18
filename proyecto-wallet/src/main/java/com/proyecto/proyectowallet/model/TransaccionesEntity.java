package com.proyecto.proyectowallet.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

import static jakarta.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "transacciones")
public class TransaccionesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String billetera_Origen;

    private String billetera_Destino;


    private String operacion;

    private String criptomoneda;

    private float cantidad;

    private LocalDate fecha;

    private String hora;

    public TransaccionesEntity(String billetera_Origen, String billetera_Destino, String operacion, String criptomoneda, float cantidad, LocalDate fecha,String hora) {
        this.billetera_Origen = billetera_Origen;
        this.billetera_Destino = billetera_Destino;
        this.operacion = operacion;
        this.criptomoneda = criptomoneda;
        this.cantidad = cantidad;
        this.fecha = fecha;
        this.hora = hora;
    }
}
