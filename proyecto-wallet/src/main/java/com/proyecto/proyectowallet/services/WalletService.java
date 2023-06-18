package com.proyecto.proyectowallet.services;

import com.proyecto.proyectowallet.model.TiposCriptoEntity;
import com.proyecto.proyectowallet.model.WalletEntity;

import java.util.List;
import java.util.Optional;

public interface WalletService {
    public String guardar(WalletEntity wallet, List<TiposCriptoEntity> divisas);
    public WalletEntity getWallet(Long id);
    public void update(WalletEntity wallet);
    public Long createHash(String email);

    public List<WalletEntity> findByIDAll(Long id);

    public void deleteAllInBatch(List <WalletEntity>wallets);


    public List<WalletEntity> getAllWallets();


    public WalletEntity buscarWalletPorHash(long hash);

    public void deleteWallet(WalletEntity wallet);

}
