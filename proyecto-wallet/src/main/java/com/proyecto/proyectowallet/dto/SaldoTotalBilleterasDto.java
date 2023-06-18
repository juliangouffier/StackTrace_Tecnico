package com.proyecto.proyectowallet.dto;

import com.proyecto.proyectowallet.model.CriptoDivisasEntity;
import com.proyecto.proyectowallet.model.WalletEntity;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaldoTotalBilleterasDto {
    private Long usuario_id;
    private List<List<CriptoDivisasEntity>> criptomonedas;
    private List<WalletEntity> billeteras;
    private float total;
}
