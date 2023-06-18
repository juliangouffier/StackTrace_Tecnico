package com.proyecto.proyectowallet.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tiposCriptos", uniqueConstraints = @UniqueConstraint(columnNames = "nombre_cripto"))

public class TiposCriptoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre_cripto;

    private float valor_cripto;



}
