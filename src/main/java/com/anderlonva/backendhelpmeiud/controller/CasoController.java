package com.anderlonva.backendhelpmeiud.controller;

import com.anderlonva.backendhelpmeiud.dto.response.CasoDTO;
import com.anderlonva.backendhelpmeiud.exceptions.BadRequestException;
import com.anderlonva.backendhelpmeiud.model.Caso;
import com.anderlonva.backendhelpmeiud.service.iface.ICasoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/casos")
@Api(value = "/casos", tags = {"Casos"})
@SwaggerDefinition(tags = { @Tag(name = "Casos", description = "Gestion api Casos")})
public class CasoController {

    @Autowired
    private ICasoService casoService;

    @ApiOperation(value = "Obtiene todos los casos", response = List.class, responseContainer = "List", produces = "application/json", httpMethod = "GET")
    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity <List<CasoDTO>> index(){
        return ResponseEntity.ok().body( casoService.consultarTodos());
    }


    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @ApiOperation(value = "Crea un Caso", response = CasoDTO.class, responseContainer = "DelitoDTO", produces = "application/json", httpMethod = "POST")
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<Caso> create(@Valid @RequestBody CasoDTO casoDTO) throws BadRequestException {
        return ResponseEntity.status(HttpStatus.CREATED).body(casoService.crear(casoDTO));
    }


}

// minuto 58
// https://drive.google.com/file/d/1mmRZm260--omcIH0MvtTad5DTsyZhYN5/view