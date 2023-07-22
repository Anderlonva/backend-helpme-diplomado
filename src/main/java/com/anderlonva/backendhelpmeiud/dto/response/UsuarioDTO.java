package com.anderlonva.backendhelpmeiud.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UsuarioDTO {

    String username;
    String nomnbre;
    String apellido;
    Boolean redSocial;
    LocalDate fechaNacimiento;
    String image;
    Boolean estado;
    Long rolId;

}
