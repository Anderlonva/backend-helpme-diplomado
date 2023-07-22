package com.anderlonva.backendhelpmeiud.controller;

import com.anderlonva.backendhelpmeiud.dto.request.DelitoDTORequest;
import com.anderlonva.backendhelpmeiud.dto.request.UsuarioDTORequest;
import com.anderlonva.backendhelpmeiud.dto.response.CasoDTO;
import com.anderlonva.backendhelpmeiud.dto.response.UsuarioDTO;
import com.anderlonva.backendhelpmeiud.exceptions.BadRequestException;
import com.anderlonva.backendhelpmeiud.service.iface.IUsuarioService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/usuarios")
@Api(value = "/usuarios", tags = {"Usuarios"})
@SwaggerDefinition(tags = { @Tag(name = "Usuarios", description = "Gestion api Usuarios")})
public class UsuarioController {

    @Autowired
    IUsuarioService iUsuarioService;

    @ApiOperation(value = "Crea un usuario", response = UsuarioDTO.class, responseContainer = "UsuarioDTO", produces = "application/json", httpMethod = "POST")
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<UsuarioDTO> create(@Valid @RequestBody UsuarioDTORequest usuarioDTORequest) throws BadRequestException {
        return ResponseEntity.status(HttpStatus.CREATED).body(iUsuarioService.guardar(usuarioDTORequest));
    }

}

// https://drive.google.com/file/d/1mmRZm260--omcIH0MvtTad5DTsyZhYN5/view