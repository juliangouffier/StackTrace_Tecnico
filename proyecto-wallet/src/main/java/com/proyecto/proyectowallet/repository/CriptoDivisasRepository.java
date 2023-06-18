package com.proyecto.proyectowallet.repository;

import com.proyecto.proyectowallet.model.CriptoDivisasEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CriptoDivisasRepository extends JpaRepository<CriptoDivisasEntity, Long> {

    @Query("SELECT p FROM CriptoDivisasEntity p WHERE p.nombre_divisa = ?1")
    public List<CriptoDivisasEntity> findByDivisa(String divisa);

    @Query("SELECT p FROM CriptoDivisasEntity p WHERE p.billetera.id = ?1")
    public List<CriptoDivisasEntity> getAllDivisasFromWalletRepo(Long id);


    @Query("SELECT c FROM CriptoDivisasEntity c WHERE c.billetera.id IN :billeteraIds")
    public List<CriptoDivisasEntity> findByBilleteraIds(@Param("billeteraIds") List<Long> billeteraIds);

    @Query("SELECT c FROM CriptoDivisasEntity c WHERE c.nombre_divisa = :nombreDivisa AND c.billetera.id = :billeteraId")
    public CriptoDivisasEntity findByNombreDivisaAndBilleteraId(@Param("nombreDivisa") String nombreDivisa, @Param("billeteraId") Long billeteraId);


}
