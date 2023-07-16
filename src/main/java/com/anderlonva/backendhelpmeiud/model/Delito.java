package com.anderlonva.backendhelpmeiud.model;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "delitos")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE) // para poner los atributos en private
public class Delito implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
     Long id;

    @Column(name = "nombre", nullable = false) // nullable = false, que no sea not null
     String nombre;

    @Column(name = "descripcion")
    String descripcion;

    @ManyToOne(fetch = FetchType.LAZY)
            @JoinColumn()
    Usuario usuario;

}
