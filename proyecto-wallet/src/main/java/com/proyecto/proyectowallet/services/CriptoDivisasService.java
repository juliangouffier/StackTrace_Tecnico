package com.proyecto.proyectowallet.services;

import com.proyecto.proyectowallet.model.CriptoDivisasEntity;
import com.proyecto.proyectowallet.model.TiposCriptoEntity;

import java.util.List;

public interface CriptoDivisasService {

    public String guardar(CriptoDivisasEntity criptoDivisasEntity);

    public void actualizarValores(TiposCriptoEntity tiposCriptoEntity, List<CriptoDivisasEntity> filas);

    public List<CriptoDivisasEntity> buscarNombre(String nombre_divisa);

    public void eliminarFilas(List<CriptoDivisasEntity> filas);


    public void eliminarArctivosPorBilletera(List<Long> ids);

    public  void update(CriptoDivisasEntity criptoDivisasEntity);


    public CriptoDivisasEntity findByNombreDivisaAndBilletera(Long id, String div);


    public List<CriptoDivisasEntity> getAllDivisasFromWallet(Long id);


}
