package com.proyecto.proyectowallet.services.impl;

import com.proyecto.proyectowallet.model.CriptoDivisasEntity;
import com.proyecto.proyectowallet.model.TiposCriptoEntity;
import com.proyecto.proyectowallet.model.WalletEntity;
import com.proyecto.proyectowallet.repository.TiposCriptoRepository;
import com.proyecto.proyectowallet.services.CriptoDivisasService;
import com.proyecto.proyectowallet.services.TiposCriptoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class TiposCriptoImpl implements TiposCriptoService {



    @Autowired
    private TiposCriptoRepository tiposCriptoRepository;
    @Autowired
    private CriptoDivisasService criptoDivisasService;

    @Override
    public String save(TiposCriptoEntity tiposCriptoEntity, List<WalletEntity> billeteras) {

        tiposCriptoRepository.save(tiposCriptoEntity);
        // agregar a todas las billeteras la criptomdivisa nueva
        if(!billeteras.isEmpty()){

            for(int i=0; i<billeteras.size(); i++){
                CriptoDivisasEntity divisa = new CriptoDivisasEntity();
                divisa.setValor_divisa(tiposCriptoEntity.getValor_cripto());
                divisa.setCantidad_divisa(0);
                divisa.setBilletera(billeteras.get(i));
                divisa.setNombre_divisa(tiposCriptoEntity.getNombre_cripto());
                criptoDivisasService.guardar(divisa);
            }
        }

        return  "exito";
    }


    @Override
    public void delete(Long id) {

        tiposCriptoRepository.deleteById(id);
    }

    @Override
    public String update(TiposCriptoEntity tiposCriptoEntity, Long id) {


        TiposCriptoEntity divisaVieja = tiposCriptoRepository.findById(id).orElse(null);
        if(divisaVieja!=null){
            String nombre = divisaVieja.getNombre_cripto();
            divisaVieja.setNombre_cripto(tiposCriptoEntity.getNombre_cripto());
            divisaVieja.setValor_cripto(tiposCriptoEntity.getValor_cripto());

            tiposCriptoRepository.saveAndFlush(divisaVieja);
            return nombre;
        }else{
            return "null";
        }

    }

    @Override
    public List<TiposCriptoEntity> findAll() {
        return tiposCriptoRepository.findAll();
    }

    @Override
    public Optional<TiposCriptoEntity> findById(Long id) {
        return tiposCriptoRepository.findById(id);
    }

    @Override
    public TiposCriptoEntity obtenerDivisa(String nombre) {
        return tiposCriptoRepository.buscarDivisa(nombre);
    }
}
