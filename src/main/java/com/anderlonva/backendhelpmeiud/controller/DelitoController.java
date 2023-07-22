package com.anderlonva.backendhelpmeiud.controller;


import com.anderlonva.backendhelpmeiud.dto.request.DelitoDTORequest;
import com.anderlonva.backendhelpmeiud.dto.response.DelitoDTO;
import com.anderlonva.backendhelpmeiud.exceptions.BadRequestException;
import com.anderlonva.backendhelpmeiud.service.iface.IDelitoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/delitos")
@Api(value = "/delitos", tags = {"Delitos"})
@SwaggerDefinition(tags = { @Tag(name = "Delitos", description = "Gestion api delitos")})
public class DelitoController {

    @Autowired
    IDelitoService delitoService;

    @ApiOperation(value = "Obtiene todos los delitos", response = List.class, responseContainer = "List", produces = "application/json", httpMethod = "GET")
    @GetMapping
    public ResponseEntity <List<DelitoDTO>> index(){
        return ResponseEntity.ok().body(delitoService.consultarTodos());
    }

    @ApiOperation(value = "Crea delito", response = DelitoDTO.class, responseContainer = "DelitoDTO", produces = "application/json", httpMethod = "POST")
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<DelitoDTO> create(@Valid @RequestBody DelitoDTORequest delitoDTORequest) throws BadRequestException {
        return ResponseEntity.status(HttpStatus.CREATED).body(delitoService.guardarDelito(delitoDTORequest));
    }

}
