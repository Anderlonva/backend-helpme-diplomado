package com.anderlonva.backendhelpmeiud.model;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Usuario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;


    @Column(name = "username", nullable = false, unique = true, length = 120)
    String username;

    @Column(name = "nombre", nullable = false, length = 120) // nullable = false, que no sea not null
    String nombre;

    @Column(name = "apellido", nullable = false, length = 120) // nullable = false, que no sea not null
    String apellido;

    @Column
    String password;

    @Column
    Boolean redSocial;

    @Column(name = "fecha_nacimiento")
    LocalDate fechaNacimiento;

    @Column
    String image;

    @Column
    Boolean estado;

    @ManyToMany(fetch = FetchType.LAZY)
            @JoinTable(
                    name = "roles_usuarios",
                    joinColumns = {@JoinColumn(name = "usuarios_id")},
                    inverseJoinColumns = {@JoinColumn(name = "roles_id")}
            )
    List<Rol> roles;

}
