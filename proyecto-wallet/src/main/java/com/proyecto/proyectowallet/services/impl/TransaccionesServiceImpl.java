package com.proyecto.proyectowallet.services.impl;

import com.proyecto.proyectowallet.model.TransaccionesEntity;
import com.proyecto.proyectowallet.repository.TransaccionesRepository;
import com.proyecto.proyectowallet.services.TransaccionesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransaccionesServiceImpl implements TransaccionesService {

    @Autowired
    private TransaccionesRepository transaccionesRepository;
    @Override
    public void guardarTransaccion(TransaccionesEntity transaccionesEntity) {
        transaccionesRepository.save(transaccionesEntity);
    }

    @Override
    public List<TransaccionesEntity> listarTransacciones() {
        return transaccionesRepository.findAll();
    }
}
