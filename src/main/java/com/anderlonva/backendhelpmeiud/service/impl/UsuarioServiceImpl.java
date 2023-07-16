package com.anderlonva.backendhelpmeiud.service.impl;

import com.anderlonva.backendhelpmeiud.dto.request.UsuarioDTORequest;
import com.anderlonva.backendhelpmeiud.dto.response.UsuarioDTO;
import com.anderlonva.backendhelpmeiud.model.Rol;
import com.anderlonva.backendhelpmeiud.model.Usuario;
import com.anderlonva.backendhelpmeiud.repository.IUsuarioRepository;
import com.anderlonva.backendhelpmeiud.service.iface.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class UsuarioServiceImpl implements IUsuarioService {

    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Override
    public List<UsuarioDTO> consultarTodos() {
        return null;
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
    public UsuarioDTO guardar(UsuarioDTORequest usuarioDTORequest) {
        Usuario usuario;
        Rol rol = new Rol();
        rol.setId(2L);
        usuario = usuarioRepository.findByUsername(usuarioDTORequest.getUsername());
        if (usuario != null){
            return null;
        }
        usuario  = new Usuario();
        usuario.setUsername(usuarioDTORequest.getUsername());
        usuario.setNombre(usuarioDTORequest.getNomnbre());
        usuario.setApellido(usuarioDTORequest.getApellido());
        usuario.setPassword(usuarioDTORequest.getPassword());
        usuario.setFechaNacimiento(usuarioDTORequest.getFechaNacimiento());
        usuario.setEstado(true);
        usuario.setImage(usuarioDTORequest.getImage());
        usuario.setRedSocial(false);
        usuario.setRoles(Collections.singletonList(rol));

        usuario = usuarioRepository.save(usuario);

        return UsuarioDTO.builder().username(usuario.getUsername()).nomnbre(usuario.getNombre()).apellido(usuario.getApellido()).fechaNacimiento(usuario.getFechaNacimiento())
                .estado(usuario.getEstado()).image(usuario.getImage()).redSocial(usuario.getRedSocial()).rolId(usuario.getRoles().get(0).getId()).build();
    }



}
