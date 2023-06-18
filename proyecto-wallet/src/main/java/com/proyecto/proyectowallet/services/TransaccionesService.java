package com.proyecto.proyectowallet.services;

import com.proyecto.proyectowallet.model.TransaccionesEntity;
import org.springframework.stereotype.Service;

import java.util.List;


public interface TransaccionesService {

    public void guardarTransaccion(TransaccionesEntity transaccionesEntity);

    public List<TransaccionesEntity> listarTransacciones();
}
