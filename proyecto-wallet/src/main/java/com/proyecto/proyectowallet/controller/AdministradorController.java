package com.proyecto.proyectowallet.controller;

import com.proyecto.proyectowallet.model.UserEntity;
import com.proyecto.proyectowallet.model.WalletEntity;
import com.proyecto.proyectowallet.services.CriptoDivisasService;
import com.proyecto.proyectowallet.services.UserService;
import com.proyecto.proyectowallet.services.WalletService;
import jakarta.validation.Valid;
import org.apache.catalina.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class AdministradorController {

    private final Logger LOGGER = LoggerFactory.getLogger(UserEntity.class);
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private WalletService walletService;
    @Autowired
    private UserService userService;
    @Autowired
    private CriptoDivisasService criptoDivisasService;

    @GetMapping("/administrador")
    public String administrador(Model model) {
        model.addAttribute("usuarios", userService.findAll());

        return "administrador";
    }

    @GetMapping("/administrador/edit/{id}")
    public String editarUsuario(@PathVariable Long id, Model model) {

        Optional<UserEntity> user = userService.findById(id);

        model.addAttribute("usuario", user.get());


        return "editarUsuario";
    }

    @PostMapping("/administrador/guardarUsuario")
    public String updateUsuario(@ModelAttribute("usuario") UserEntity user, Model model) throws IOException {


        if (user.getPassword().equals(userService.findById(user.getId()).get().getPassword())) {
            userService.modificarUsuario(user);
        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userService.modificarUsuario(user);
        }


        return "redirect:/administrador";
    }

    @PostMapping("/administrador/delete/{id}")
    public String eliminarUsuario(@PathVariable Long id) {
        List<WalletEntity> wallets = walletService.findByIDAll(id);

        if (wallets.size()>0) {

            List<Long> ids = new ArrayList<>();
            for(int i=0; i<wallets.size();i++){
                ids.add(wallets.get(i).getId());

            }
            LOGGER.info(ids.toString());
            criptoDivisasService.eliminarArctivosPorBilletera(ids); //3


            userService.deleteUser(id);
        } else{
            userService.deleteUser(id);
        }
        return "redirect:/administrador";

    }

    @GetMapping("/administrador/crearUsuario")
    public String crearUsuario(Model model){
        model.addAttribute("usuario",new UserEntity());
        return "crearUsuario";
    }
    @PostMapping("/administrador/crearUsuario")
    public String crearUsuarioNuevo(@ModelAttribute("usuario") UserEntity user){

        userService.saveUser(user);

        return "redirect:/administrador";
    }

    @GetMapping("/administradorBilleteras")
    public String admbilleteras(Model model){
        List<WalletEntity> wallets = walletService.getAllWallets();
        model.addAttribute("billeteras",wallets);
        return "administradorBilleteras";
    }

    @PostMapping("/administradorBilleteras/delete/{id}")
    public String deleteBilletera(@PathVariable Long id){

        try{
            WalletEntity wallet = walletService.getWallet(id);
            if(wallet!=null){
                List<Long> billetera = new ArrayList<>();
                billetera.add(id);
                criptoDivisasService.eliminarArctivosPorBilletera(billetera);
                walletService.deleteWallet(wallet);
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return "redirect:/administradorBilleteras/";
    }

}