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
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.security.PermitAll;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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


    @PostMapping("/upload")
    public ResponseEntity<?> upload(@RequestParam("image") MultipartFile image, Authentication authentication) throws RestException{
        // TODO: PASAR LOGICA DE NEGOCIOS AL SERVICE
        Map<String, Object> response = new HashMap<>();
        Usuario usuario = iUsuarioService.findByUsername(authentication.getName());
        if(!image.isEmpty()) {
            String nombreImage = UUID.randomUUID().toString()+"_"+image.getOriginalFilename().replace(" ", "");
            Path path = Paths.get("uploads").resolve(nombreImage).toAbsolutePath();
            try {
                Files.copy(image.getInputStream(), path);
            }catch (IOException e) {
                response.put("Error", e.getMessage().concat(e.getCause().getMessage()));
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }

            String oldImage = usuario.getImage();

            if(oldImage != null && oldImage.length() > 0 && !oldImage.startsWith("http")) {
                Path oldPath = Paths.get("uploads").resolve(oldImage).toAbsolutePath();
                File oldFileImage = oldPath.toFile();
                if(oldFileImage.exists() && oldFileImage.canRead()) {
                    oldFileImage.delete();
                }
            }

            usuario.setImage(nombreImage);
            iUsuarioService.actualizar(usuario);
            response.put("usuario", usuario);
        }
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    @GetMapping("/uploads/img/{name:.+}")
    public ResponseEntity<Resource> getImage(@PathVariable String name) throws InternalServerErrorException {
        Resource resource = iUsuarioService.getImage(name);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+ resource.getFilename() + "\"");
        return new ResponseEntity<Resource>(resource, headers, HttpStatus.OK);

    }



}

// minuto 2:53
// https://drive.google.com/file/d/1mmRZm260--omcIH0MvtTad5DTsyZhYN5/view