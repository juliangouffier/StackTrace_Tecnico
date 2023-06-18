package com.proyecto.proyectowallet.services.impl;

import com.proyecto.proyectowallet.model.CriptoDivisasEntity;
import com.proyecto.proyectowallet.model.TiposCriptoEntity;
import com.proyecto.proyectowallet.model.UserEntity;
import com.proyecto.proyectowallet.repository.CriptoDivisasRepository;
import com.proyecto.proyectowallet.services.CriptoDivisasService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CriptoDivisasImpl implements CriptoDivisasService {

    private final Logger LOGGER = LoggerFactory.getLogger(UserEntity.class);
    @Autowired
    private CriptoDivisasRepository criptoDivisasRepository;
    @Override
    public String guardar(CriptoDivisasEntity criptoDivisasEntity) {

        criptoDivisasRepository.save(criptoDivisasEntity);
        return "exitoso";
    }

    @Override
    public void actualizarValores(TiposCriptoEntity tiposCriptoEntity, List<CriptoDivisasEntity> filas) {

        if(!filas.isEmpty()){
            for(int i=0; i<filas.size();i++){
                filas.get(i).setNombre_divisa(tiposCriptoEntity.getNombre_cripto());
                filas.get(i).setValor_divisa(tiposCriptoEntity.getValor_cripto());
                this.guardar(filas.get(i));
            }

        }
    }


    @Override
    public List<CriptoDivisasEntity> buscarNombre(String nombre_divisa) {
        return criptoDivisasRepository.findByDivisa(nombre_divisa);

    }

    @Override
    public void eliminarFilas(List<CriptoDivisasEntity> filas) {
        if(!filas.isEmpty()){
            for(int i=0; i<filas.size();i++){
                criptoDivisasRepository.delete(filas.get(i));

            }

        }
    }

    @Override
    public void eliminarArctivosPorBilletera(List<Long> ids) {

        List<CriptoDivisasEntity> filas1 = criptoDivisasRepository.findByBilleteraIds(ids);
        for(int i =0; i<filas1.size();i++){
           criptoDivisasRepository.delete(filas1.get(i));

        }


    }

    @Override
    public void update(CriptoDivisasEntity criptoDivisasEntity) {
        criptoDivisasRepository.save(criptoDivisasEntity);
    }

    @Override
    public CriptoDivisasEntity findByNombreDivisaAndBilletera(Long id, String divisa) {
        return criptoDivisasRepository.findByNombreDivisaAndBilleteraId(divisa,id);
    }

    @Override
    public List<CriptoDivisasEntity> getAllDivisasFromWallet(Long id) {

        return criptoDivisasRepository.getAllDivisasFromWalletRepo(id);
    }


}
