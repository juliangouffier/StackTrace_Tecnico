package com.proyecto.proyectowallet.services;

import com.proyecto.proyectowallet.model.UserEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService {
    //public UserEntity saveUser(RegisterDto registerDto);
    public UserEntity saveUser(UserEntity userEntity);
    public UserEntity findByEmail(String email);

    Optional<UserEntity> findById(Long id);

    Optional<UserEntity> buscarEmail(String email);

    void modificarUsuario(UserEntity userEntity);

    List<UserEntity> findAll();

    void deleteUser(Long id);


    List<UserEntity> listarUsuarios();


}
