package com.proyecto.proyectowallet.controller;

import com.proyecto.proyectowallet.services.TransaccionesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TransaccionesController {


    @Autowired
    TransaccionesService transaccionesService;

    @GetMapping("/transacciones")
    public String listarTransacciones(Model model){
        model.addAttribute("transacciones", transaccionesService.listarTransacciones());

        return "transacciones";
    }
}
