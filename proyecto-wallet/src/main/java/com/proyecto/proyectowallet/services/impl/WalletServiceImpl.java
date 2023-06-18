package com.proyecto.proyectowallet.services.impl;

import com.proyecto.proyectowallet.model.CriptoDivisasEntity;
import com.proyecto.proyectowallet.model.TiposCriptoEntity;
import com.proyecto.proyectowallet.model.UserEntity;
import com.proyecto.proyectowallet.model.WalletEntity;
import com.proyecto.proyectowallet.repository.WalletRepository;
import com.proyecto.proyectowallet.services.CriptoDivisasService;
import com.proyecto.proyectowallet.services.TiposCriptoService;
import com.proyecto.proyectowallet.services.WalletService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class WalletServiceImpl implements WalletService {
    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private CriptoDivisasService criptoDivisasService;


    private final Logger LOGGER = LoggerFactory.getLogger(UserEntity.class);

    @Override
    public String guardar(WalletEntity wallet, List<TiposCriptoEntity> divisas){
        try{




            LOGGER.info("Lista {}", divisas.toString() );



            if(!divisas.isEmpty()){
                walletRepository.save(wallet);
                TiposCriptoEntity div;
                for(int i =0; i<divisas.size(); i++){
                    CriptoDivisasEntity entidadCripto = new CriptoDivisasEntity();

                    div = divisas.get(i);
                    entidadCripto.setBilletera(wallet);
                    entidadCripto.setCantidad_divisa(0);
                    entidadCripto.setNombre_divisa(div.getNombre_cripto());
                    entidadCripto.setValor_divisa(div.getValor_cripto());

                    LOGGER.info("divisas {}", divisas.get(i).toString() );
                    LOGGER.info("Lista {}", entidadCripto.getNombre_divisa() );
                    String texto = criptoDivisasService.guardar(entidadCripto);
                    LOGGER.info(texto);

                }

            }else{
                return "0";
            }



            return "1"; //exito
        }catch(Exception e){

        return "0"; //fallo
        }

    }

    @Override
    public WalletEntity getWallet(Long id) {
        return walletRepository.findByID(id);
    }

    @Override
    public void update(WalletEntity wallet) {
        walletRepository.save(wallet);
    }

    @Override
    public Long createHash(String email) {
        Random random = new Random();
        Long n = random.nextLong();
        if(n<0){
            n = n*-1;
        }

        return email.hashCode()+n;
    }

    @Override
    public List<WalletEntity> findByIDAll(Long id) {
        try{
            return walletRepository.findByUsuario_id(id);
         //   return walletRepository.findAllById(Collections.singleton(id));
        }catch (Exception e){
            return null;
        }

    }

    @Override
    public void deleteAllInBatch(List<WalletEntity> wallets) {

        walletRepository.deleteAllInBatch(wallets);
    }

    @Override
    public List<WalletEntity> getAllWallets() {
        return walletRepository.findAll();
    }

    @Override
    public WalletEntity buscarWalletPorHash(long hash) {
        return walletRepository.findByHash(hash);
    }

    @Override
    public void deleteWallet(WalletEntity wallet) {
        walletRepository.delete(wallet);
    }


}
