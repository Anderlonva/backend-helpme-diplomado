package com.anderlonva.backendhelpmeiud.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DelitoDTORequest {

    @NotNull(message = "Nombre no puede ser nulo")
    @NotEmpty(message = "Nombre no puede ser vacio")
    String nombre;
    String descripcion;

    @NotNull(message = "debe ingresar id usuario")
    @JsonProperty("usuario_id")
    Long usuarioId;



}
