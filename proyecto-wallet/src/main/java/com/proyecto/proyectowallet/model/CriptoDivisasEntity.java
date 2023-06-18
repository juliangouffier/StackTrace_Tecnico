package com.proyecto.proyectowallet.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "criptodivisas")
public class CriptoDivisasEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Long id;

    @Column
    private String nombre_divisa;

    @Column
    private float valor_divisa;

    @Column
    private float cantidad_divisa;




    @ManyToOne
    private WalletEntity billetera;


    public CriptoDivisasEntity(String nombre_divisa, float valor_divisa, float cantidad_divisa, WalletEntity billetera) {
        this.nombre_divisa = nombre_divisa;
        this.valor_divisa = valor_divisa;
        this.cantidad_divisa = cantidad_divisa;
        this.billetera = billetera;
    }
}
