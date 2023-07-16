package com.anderlonva.backendhelpmeiud.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;


@Entity
@Table(name = "casos")
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Caso implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    Long id;

    @Column
    LocalDateTime fechaHora;

    @Column
    Float latitud;

    @Column
    Float longitud;

    @Column
    Float altitud;

    @Column
    String descripcion;

    @Column
    Boolean esvisible;

    @ManyToOne
            @JoinColumn(name = "usuarios_id")
    Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "delitos_id")
    Delito delito;

    @PrePersist
    public void prePersist(){
        if(fechaHora == null ){
            fechaHora = LocalDateTime.now();
        }
    }

}
