package com.proyecto.proyectowallet.dto;

import com.proyecto.proyectowallet.model.CriptoDivisasEntity;
import lombok.*;

import java.util.List;
@Getter
@Setter
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaldoBilleterasDto {
    private Long id;
    private List<CriptoDivisasEntity> criptomonedas;
    private float total;
}
