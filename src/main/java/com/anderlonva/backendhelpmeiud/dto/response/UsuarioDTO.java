package com.anderlonva.backendhelpmeiud.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UsuarioDTO {

    Long id;
    String username;
    String nombre;
    String apellido;
    Boolean redSocial;
    LocalDate fechaNacimiento;
    String image;
    Boolean estado;
    Long rolId;
    List<String> roles;

}
