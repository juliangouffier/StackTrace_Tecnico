package com.proyecto.proyectowallet.repository;

import com.proyecto.proyectowallet.model.CriptoDivisasEntity;
import com.proyecto.proyectowallet.model.TiposCriptoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TiposCriptoRepository extends JpaRepository<TiposCriptoEntity,Long> {

    @Query("SELECT p FROM TiposCriptoEntity p WHERE p.nombre_cripto = ?1")
    public TiposCriptoEntity buscarDivisa(String divisa);
}
