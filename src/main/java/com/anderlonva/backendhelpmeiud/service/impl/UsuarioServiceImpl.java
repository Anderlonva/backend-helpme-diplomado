package com.anderlonva.backendhelpmeiud.service.impl;

import com.anderlonva.backendhelpmeiud.dto.request.UsuarioDTORequest;
import com.anderlonva.backendhelpmeiud.dto.response.DelitoDTO;
import com.anderlonva.backendhelpmeiud.dto.response.UsuarioDTO;
import com.anderlonva.backendhelpmeiud.exceptions.BadRequestException;
import com.anderlonva.backendhelpmeiud.exceptions.ErrorDto;
import com.anderlonva.backendhelpmeiud.model.Rol;
import com.anderlonva.backendhelpmeiud.model.Usuario;
import com.anderlonva.backendhelpmeiud.repository.IUsuarioRepository;
import com.anderlonva.backendhelpmeiud.service.iface.IUsuarioService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UsuarioServiceImpl implements IUsuarioService , UserDetailsService {

    @Value("${emailserver.enabled}")
    private Boolean emailEnabled;

    @Autowired
    private EmailService emailService;

    @Lazy
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Override
    public List<UsuarioDTO> consultarTodos() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        List<UsuarioDTO> usuarioDTOS = usuarios.stream().map(usuario -> {
            return UsuarioDTO.builder().username(usuario.getUsername()).nomnbre(usuario.getNombre()).apellido(usuario.getApellido())
                    .fechaNacimiento(usuario.getFechaNacimiento()).image(usuario.getImage()).redSocial(usuario.getRedSocial()).estado(usuario.getEstado())
                    .build(); // falta llamar el id del rol
        }).collect(Collectors.toList());

        return usuarioDTOS;
    }

    @Override
    public UsuarioDTO consultarPorId(Long id) {
        return null;
    }

    @Override
    public UsuarioDTO consultarPorUsername(String username) {
        return null;
    }

    @Override
    public UsuarioDTO guardar(UsuarioDTORequest usuarioDTORequest) throws BadRequestException {
        Usuario usuario;
        Rol rol = new Rol();
        rol.setId(2L);
        usuario = usuarioRepository.findByUsername(usuarioDTORequest.getUsername());
        if (usuario != null){
            throw new BadRequestException(
                    ErrorDto.builder()
                            .status(HttpStatus.BAD_REQUEST.value())
                            .message("Este usuario ya existe")
                            .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                            .date(LocalDateTime.now())
                            .build()
            );
        }



        usuario  = new Usuario();
       // log.info("password cifrada{}", passwordEncoder.encode(usuarioDTORequest.getPassword()));
        usuario.setUsername(usuarioDTORequest.getUsername());
        usuario.setNombre(usuarioDTORequest.getNomnbre());
        usuario.setApellido(usuarioDTORequest.getApellido());
        usuario.setPassword(passwordEncoder.encode(usuarioDTORequest.getPassword())); // ciframos el password
        usuario.setFechaNacimiento(usuarioDTORequest.getFechaNacimiento());
        usuario.setEstado(true);
        usuario.setImage(usuarioDTORequest.getImage());
        usuario.setRedSocial(false);
        usuario.setRoles(Collections.singletonList(rol));

        usuario = usuarioRepository.save(usuario);

        if(usuario!= null && usuario.getUsername() != null) {
            if(Boolean.TRUE.equals(emailEnabled)) {
                String mensaje = "Su usuario: "+usuario.getUsername()+"; password: "+usuarioDTORequest.getPassword();
                String asunto = "Registro en HelmeIUD";
                emailService.sendEmail(
                        mensaje,
                        usuario.getUsername(),
                        asunto
                );
            }

        }

        return UsuarioDTO.builder().username(usuario.getUsername()).nomnbre(usuario.getNombre()).apellido(usuario.getApellido()).fechaNacimiento(usuario.getFechaNacimiento())
                .estado(usuario.getEstado()).image(usuario.getImage()).redSocial(usuario.getRedSocial()).rolId(usuario.getRoles().get(0).getId()).build();
    }

    @Override
    @Transactional(readOnly = true)
    public Usuario findByUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByUsername(username);
        if(usuario == null) {
            log.error("Error de login, no existe usuario: "+ usuario);
            throw new UsernameNotFoundException("Error de login, no existe usuario: "+ username);
        }
        List<GrantedAuthority> authorities = new ArrayList<>();
        for(Rol role: usuario.getRoles()) {
            GrantedAuthority authority = new SimpleGrantedAuthority(role.getNombre());
            log.info("Rol {}", authority.getAuthority());
            authorities.add(authority);
        }
        return new User(usuario.getUsername(), usuario.getPassword(), usuario.getEstado(), true, true, true,authorities);
    }
}
// minuto 1:54
//  https://drive.google.com/file/d/1UED4IOrObUyQyQlelzeN9U2Rfmsrk14z/view