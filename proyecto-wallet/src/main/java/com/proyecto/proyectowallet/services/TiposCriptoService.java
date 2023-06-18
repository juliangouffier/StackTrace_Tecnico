package com.proyecto.proyectowallet.services;

import com.proyecto.proyectowallet.model.TiposCriptoEntity;
import com.proyecto.proyectowallet.model.WalletEntity;

import java.util.List;
import java.util.Optional;

public interface TiposCriptoService{


    public String save(TiposCriptoEntity tiposCriptoEntity, List<WalletEntity> billeteras);

    public void delete(Long id);

    public String update(TiposCriptoEntity tiposCriptoEntity, Long id);

    public List<TiposCriptoEntity> findAll();

    public Optional<TiposCriptoEntity> findById(Long id);


    public TiposCriptoEntity obtenerDivisa(String nombre);
}
