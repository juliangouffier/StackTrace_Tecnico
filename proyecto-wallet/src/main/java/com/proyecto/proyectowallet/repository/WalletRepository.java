package com.proyecto.proyectowallet.repository;

import com.proyecto.proyectowallet.model.CriptoDivisasEntity;
import com.proyecto.proyectowallet.model.WalletEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WalletRepository extends JpaRepository<WalletEntity,Long> {

List<WalletEntity> findByUsuario_id(Long usuario_id);

    @Query("SELECT p FROM WalletEntity p WHERE p.id = ?1")
    public WalletEntity findByID(Long id);

    @Query("SELECT p FROM WalletEntity p WHERE p.hash = ?1")
    public WalletEntity findByHash(long hash);
}
