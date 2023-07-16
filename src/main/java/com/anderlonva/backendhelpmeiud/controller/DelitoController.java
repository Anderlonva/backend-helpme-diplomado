package com.anderlonva.backendhelpmeiud.controller;


import com.anderlonva.backendhelpmeiud.dto.request.DelitoDTORequest;
import com.anderlonva.backendhelpmeiud.dto.response.DelitoDTO;
import com.anderlonva.backendhelpmeiud.service.iface.IDelitoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/delitos")
public class DelitoController {

    @Autowired
    IDelitoService delitoService;

    @GetMapping
    public ResponseEntity <List<DelitoDTO>> index(){
        return ResponseEntity.ok().body(delitoService.consultarTodos());
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<DelitoDTO> create(@Valid @RequestBody DelitoDTORequest delitoDTORequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(delitoService.guardarDelito(delitoDTORequest));
    }

}
