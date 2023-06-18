package com.proyecto.proyectowallet.repository;

import com.proyecto.proyectowallet.model.TransaccionesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransaccionesRepository extends JpaRepository<TransaccionesEntity,Long> {
}
