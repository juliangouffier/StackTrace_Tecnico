package com.proyecto.proyectowallet.services.impl;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.proyecto.proyectowallet.model.RolEntity;
import com.proyecto.proyectowallet.model.UserEntity;
import com.proyecto.proyectowallet.repository.UserRepository;
import com.proyecto.proyectowallet.services.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {


    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;


    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }



    @Override
    public UserEntity saveUser(UserEntity userEntity) {
        UserEntity user = new UserEntity(userEntity.getApellido(),userEntity.getDni(),userEntity.getEmail(),userEntity.getNombre(),passwordEncoder.encode(userEntity.getPassword()),userEntity.getSexo(),userEntity.getTelefono(),Arrays.asList(new RolEntity("ROLE_USER")));

        return this.userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(username);
        if(userEntity == null){
            throw new UsernameNotFoundException("Email o password invalidos");
        }
            return new User(userEntity.getEmail(),userEntity.getPassword(),mapearAutoridadesRoles(userEntity.getRoles()));


    }
    private Collection<? extends GrantedAuthority> mapearAutoridadesRoles(Collection<RolEntity> roles){
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getNombre())).collect(Collectors.toList());
    }

    @Override
    public UserEntity findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<UserEntity> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<UserEntity> buscarEmail(String email) {
        return Optional.ofNullable(userRepository.findByEmail(email));
    }

    @Override
    public void modificarUsuario(UserEntity userEntity) {
        String password = userEntity.getPassword();
        userEntity.setPassword(passwordEncoder.encode(password));
        userRepository.save(userEntity);

    }

    @Override
    public List<UserEntity> findAll() {
        return userRepository.findAll();
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }


    @Override
    public List<UserEntity> listarUsuarios() {
        return userRepository.findAll();
    }


}
