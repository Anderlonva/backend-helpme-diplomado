package com.anderlonva.backendhelpmeiud.controller;

import com.anderlonva.backendhelpmeiud.dto.request.DelitoDTORequest;
import com.anderlonva.backendhelpmeiud.dto.request.UsuarioDTORequest;
import com.anderlonva.backendhelpmeiud.dto.response.CasoDTO;
import com.anderlonva.backendhelpmeiud.dto.response.UsuarioDTO;
import com.anderlonva.backendhelpmeiud.exceptions.*;
import com.anderlonva.backendhelpmeiud.model.Usuario;
import com.anderlonva.backendhelpmeiud.service.iface.IUsuarioService;
import com.anderlonva.backendhelpmeiud.util.ConstUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;
import javax.validation.Valid;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/usuarios")
@Api(value = "/usuarios", tags = {"Usuarios"})
@SwaggerDefinition(tags = { @Tag(name = "Usuarios", description = "Gestion api Usuarios")})
public class UsuarioController {

    @Autowired
    private IUsuarioService iUsuarioService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @ApiOperation(value = "Crea un usuario", response = UsuarioDTO.class, responseContainer = "UsuarioDTO", produces = "application/json", httpMethod = "POST")
    @PostMapping("/signup")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<UsuarioDTO> create(@Valid @RequestBody UsuarioDTORequest usuarioDTORequest) throws BadRequestException {
        return ResponseEntity.status(HttpStatus.CREATED).body(iUsuarioService.guardar(usuarioDTORequest));
    }

    @ApiOperation(value = "Consulta el usuario autenticado actual",
            response = UsuarioDTO.class,
            produces = "application/json",
            httpMethod = "GET")
    @GetMapping("/usuario")
    public ResponseEntity<UsuarioDTO> userInfo(Authentication authentication) throws RestException {
        return ResponseEntity.ok().body(iUsuarioService.userInfo(authentication));
    }

    @ApiOperation(value = "Actualiza un usuario",
            response = Usuario.class,
            produces = "application/json",
            httpMethod = "PUT")
    @PutMapping("/usuario")
    public ResponseEntity<Usuario> update(Authentication authentication, @RequestBody Usuario usuario) throws RestException {
        try {
            if(!authentication.isAuthenticated()) {
                throw new RestException(
                        ErrorDto.builder()
                                .error(HttpStatus.UNAUTHORIZED.getReasonPhrase())
                                .message(ConstUtil.MESSAGE_NOT_AUTHORIZED)
                                .status(HttpStatus.UNAUTHORIZED.value())
                                .date(LocalDateTime.now())
                                .build()
                );
            }
            Usuario usuarioFind = iUsuarioService.findByUsername((authentication.getName()));
            if(usuarioFind == null) {
                throw new NotFoundException(
                        ErrorDto.builder()
                                .error(HttpStatus.NOT_FOUND.getReasonPhrase())
                                .message(ConstUtil.MESSAGE_NOT_FOUND)
                                .status(HttpStatus.NOT_FOUND.value())
                                .date(LocalDateTime.now())
                                .build()
                );
            }
            usuarioFind.setNombre(usuario.getNombre());
            usuarioFind.setApellido(usuario.getApellido());
            usuarioFind.setFechaNacimiento(usuario.getFechaNacimiento());
            if(usuario.getPassword() != null) {
                usuarioFind.setPassword(passwordEncoder.encode(usuario.getPassword()));
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(iUsuarioService.actualizar(usuarioFind));
        }catch (BadRequestException ex){
            throw ex;
        }catch (Exception ex){
            // log.error("Error {}", ex);
            throw new InternalServerErrorException(
                    ErrorDto.builder()
                            .error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                            .message(ConstUtil.MESSAGE_GENERAL)
                            .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                            .date(LocalDateTime.now())
                            .build()
            );
        }
    }



}

// minuto 58
// https://drive.google.com/file/d/1mmRZm260--omcIH0MvtTad5DTsyZhYN5/view