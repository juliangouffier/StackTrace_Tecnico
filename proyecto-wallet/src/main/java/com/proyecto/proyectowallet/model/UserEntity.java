package com.proyecto.proyectowallet.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.Collection;
import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class UserEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "dni")
    private String dni;
    @Column(name = "email")
    private String email;
    @Column(name = "sexo")
    private String sexo;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "apellido")
    private String apellido;
    @Column(name = "telefono")
    private String telefono;
    @Column(name = "password")
    private String password;
    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(
            name = "usuarios_roles",
            joinColumns = @JoinColumn(name = "usuario_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "rol_id",referencedColumnName = "id")
    )
    private Collection<RolEntity> roles;
    @JsonManagedReference
     @OneToMany(mappedBy = "usuario",cascade = CascadeType.REMOVE)
     private List<WalletEntity> billeteras;

    public UserEntity(String apellido, String dni, String email, String nombre, String password, String sexo, String telefono,Collection<RolEntity> roles) {
        this.dni = dni;
        this.email = email;
        this.sexo = sexo;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.roles = roles;
        this.password = password;
    }


}
