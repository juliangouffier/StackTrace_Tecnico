package com.proyecto.proyectowallet.controller;

import com.proyecto.proyectowallet.model.CriptoDivisasEntity;
import com.proyecto.proyectowallet.model.TiposCriptoEntity;
import com.proyecto.proyectowallet.model.UserEntity;
import com.proyecto.proyectowallet.model.WalletEntity;
import com.proyecto.proyectowallet.services.CriptoDivisasService;
import com.proyecto.proyectowallet.services.TiposCriptoService;
import com.proyecto.proyectowallet.services.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/divisas")
public class DivisasController {
    @Autowired
    private TiposCriptoService tiposCriptoService;

    @Autowired
    private CriptoDivisasService criptoDivisasService;
    @Autowired
    private WalletService walletService;
    @GetMapping
    public String divisas(Model model){
        model.addAttribute("divisas", tiposCriptoService.findAll());
        return "divisas";
    }

    @GetMapping("/createDivisa")
    public String create(Model model){
        model.addAttribute("nueva",new TiposCriptoEntity());
        return "createDivisa";

    }

    @PostMapping("/createDivisa")
    public String createNew(@ModelAttribute("nueva") TiposCriptoEntity cripto){
       List<WalletEntity> billeteras = walletService.getAllWallets();
        tiposCriptoService.save(cripto,billeteras);
        return "redirect:/divisas";
    }

    @GetMapping("/edit/{id}")
    public String modificarDivisaId(@PathVariable Long id, Model model){
        Optional<TiposCriptoEntity> divisa = tiposCriptoService.findById(id);


        model.addAttribute("divisa", divisa.get());
        model.addAttribute("id",id);
        return "modificarDivisa";
    }
    @PostMapping("/modificarDivisa/{id}")
    public String modificarDivisa(@PathVariable Long id,@ModelAttribute("divisa") TiposCriptoEntity tiposCriptoEntity){


        tiposCriptoService.update(tiposCriptoEntity,id);
        List<CriptoDivisasEntity>filas= criptoDivisasService.buscarNombre(tiposCriptoEntity.getNombre_cripto());
        criptoDivisasService.actualizarValores(tiposCriptoEntity,filas);

        return "redirect:/divisas";
    }

    @GetMapping("/delete/{id}")
    public String eliminarDivisa(@PathVariable Long id, Model model){

        List<CriptoDivisasEntity> filas = criptoDivisasService.buscarNombre(tiposCriptoService.findById(id).get().getNombre_cripto());

        criptoDivisasService.eliminarFilas(filas);

        tiposCriptoService.delete(id);




        return "redirect:/divisas";
    }

}
