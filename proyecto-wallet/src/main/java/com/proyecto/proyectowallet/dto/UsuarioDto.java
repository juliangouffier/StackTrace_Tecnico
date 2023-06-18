package com.proyecto.proyectowallet.dto;


import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UsuarioDto {
    private Long id;
    @NotEmpty
    private String dni;
    @NotEmpty
    private String sexo;
    @NotEmpty
    private String nombre;
    @NotEmpty
    private String apellido;
    @NotEmpty
    private String email;
    @NotEmpty
    private String telefono;

    private String role;
    @NotEmpty
    private String password;





    public Long getId() {
        return id;
    }

    public String getDni() {
        return dni;
    }

    public String getSexo() {
        return sexo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getRole() {
        return role;
    }

    public String getPassword() {
        return password;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
