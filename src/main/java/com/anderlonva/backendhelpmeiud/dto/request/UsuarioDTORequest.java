package com.anderlonva.backendhelpmeiud.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UsuarioDTORequest {

    @NotNull(message = "username obligatorio")
    @Email(message = "No cumple formato email")
    String username;

    @NotNull(message = "nombre obligatorio")
    @NotBlank(message = "nombre requerido")
    String nombre;

    @Size(min = 2, max = 120)
    String apellido;

    @Size(min = 5, message = "debe suministrar contraseña segura")
    String password;

    @JsonProperty("red_social")
    Boolean redSocial;

    @JsonProperty("fecha_nacimiento")
    LocalDate fechaNacimiento;


    String image;

    Boolean estado;

}
