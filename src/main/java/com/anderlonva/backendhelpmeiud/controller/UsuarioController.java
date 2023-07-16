package com.anderlonva.backendhelpmeiud.controller;

import com.anderlonva.backendhelpmeiud.dto.request.DelitoDTORequest;
import com.anderlonva.backendhelpmeiud.dto.request.UsuarioDTORequest;
import com.anderlonva.backendhelpmeiud.dto.response.UsuarioDTO;
import com.anderlonva.backendhelpmeiud.service.iface.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    IUsuarioService iUsuarioService;

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<UsuarioDTO> create(@Valid @RequestBody UsuarioDTORequest usuarioDTORequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(iUsuarioService.guardar(usuarioDTORequest));
    }

}
