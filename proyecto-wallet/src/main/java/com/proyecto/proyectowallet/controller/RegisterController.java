package com.proyecto.proyectowallet.controller;

import ch.qos.logback.core.model.Model;
import com.proyecto.proyectowallet.model.UserEntity;
import com.proyecto.proyectowallet.services.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class RegisterController {
    @Autowired
    private UserService userService;


    private final Logger LOGGER = LoggerFactory.getLogger(UserEntity.class);

    @ModelAttribute("usuario")
    public UserEntity returnNewUser(){
        return new UserEntity();
    }
    @GetMapping("/register")
    public String register(){
        return "login";

    }
    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("usuario") UserEntity user,
                           BindingResult result, Model model) {
        UserEntity existingUserEmail = userService.findByEmail(user.getEmail());
        if(existingUserEmail != null && existingUserEmail.getEmail() != null && !existingUserEmail.getEmail().isEmpty()) {
            return "redirect:/register?fail";
        }


        userService.saveUser(user);
        return "redirect:/register?exito";
    }
    @GetMapping("/")
    public String inicio(){
        return "login";
    }


    @GetMapping("/login")
    public String iniciarSesion() {
        return "login";
    }

    @PostMapping("/login")
    public String post(UserEntity usuario, HttpSession session) {

        Optional<UserEntity> user = userService.buscarEmail(usuario.getEmail());
        return "login";
    }

}
