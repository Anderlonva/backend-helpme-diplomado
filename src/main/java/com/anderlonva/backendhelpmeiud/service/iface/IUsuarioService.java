package com.anderlonva.backendhelpmeiud.service.iface;



import com.anderlonva.backendhelpmeiud.dto.request.UsuarioDTORequest;
import com.anderlonva.backendhelpmeiud.dto.response.UsuarioDTO;
import com.anderlonva.backendhelpmeiud.exceptions.BadRequestException;
import com.anderlonva.backendhelpmeiud.model.Usuario;

import java.util.List;

public interface IUsuarioService {

    List<UsuarioDTO> consultarTodos();

    UsuarioDTO consultarPorId(Long id);

    UsuarioDTO consultarPorUsername(String username);

    UsuarioDTO guardar(UsuarioDTORequest usuarioDTORequest) throws BadRequestException;

    Usuario findByUsername(String username);

}
