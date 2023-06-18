package com.proyecto.proyectowallet.dto;


import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TransferenciaDto {

    private Long walletOrigen;

    private Long walletDestino;

    private float cantidad;

    private String criptoDivisa;

    private String estado;
}
