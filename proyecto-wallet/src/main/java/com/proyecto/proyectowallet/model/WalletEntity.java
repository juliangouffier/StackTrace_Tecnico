package com.proyecto.proyectowallet.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "wallets", uniqueConstraints = @UniqueConstraint(columnNames = "hash"))
public class WalletEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Long id;

    @Column
    private Long hash;
    @JsonBackReference
    @ManyToOne
    private UserEntity usuario;
    @JsonIgnore
    @OneToMany(mappedBy = "billetera")
    private List<CriptoDivisasEntity> criptodivisas;



/*

    @ManyToMany
    @JoinTable(
            name = "billetera_criptodivisa",
            joinColumns = @JoinColumn(name = "id"),
            inverseJoinColumns = @JoinColumn(name = "criptodivisa_id")
    )
    private List<CriptoDivisasEntity> criptodivisas;
    @ElementCollection
    @CollectionTable(name = "billetera_cantidad_cripto", joinColumns = @JoinColumn(name = "id"))
    @MapKeyJoinColumn(name = "criptodivisa_id")
    @Column(name = "cantidad")
    private Map<CriptoDivisasEntity, Double> cantidadCriptodivisas;  // Mapa para almacenar la cantidad de criptodivisas en la billetera
*/

    public WalletEntity(Long hash, UserEntity usuario, List<CriptoDivisasEntity> criptodivisas) {
        this.hash = hash;
        this.usuario = usuario;
        this.criptodivisas = criptodivisas;
    }

}
