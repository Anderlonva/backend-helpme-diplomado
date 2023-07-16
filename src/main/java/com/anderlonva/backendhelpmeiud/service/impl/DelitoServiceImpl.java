package com.anderlonva.backendhelpmeiud.service.impl;

import com.anderlonva.backendhelpmeiud.dto.request.DelitoDTORequest;
import com.anderlonva.backendhelpmeiud.dto.response.DelitoDTO;
import com.anderlonva.backendhelpmeiud.model.Delito;
import com.anderlonva.backendhelpmeiud.model.Usuario;
import com.anderlonva.backendhelpmeiud.repository.IDelitoRepository;
import com.anderlonva.backendhelpmeiud.repository.IUsuarioRepository;
import com.anderlonva.backendhelpmeiud.service.iface.IDelitoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DelitoServiceImpl implements IDelitoService {

    @Autowired
    private IDelitoRepository delitoRepository;

    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Override
    @Transactional(readOnly = true)
    public List<DelitoDTO> consultarTodos() {
       List<Delito> delitos = delitoRepository.findAll();

       List<DelitoDTO> delitoDTOS = delitos.stream().map(delito -> {
            return DelitoDTO.builder().id(delito.getId()).nombre(delito.getNombre()).descripcion(delito.getDescripcion()).build();
       }).collect(Collectors.toList());

       return delitoDTOS;
    }

    @Override
    public DelitoDTO consultarPorId(Long id) {
        return null;
    }

    @Override
    @Transactional
    public DelitoDTO guardarDelito(DelitoDTORequest delitoDTORequest) {
        Delito delito = new Delito();
        delito.setNombre(delitoDTORequest.getNombre());
        delito.setDescripcion(delitoDTORequest.getDescripcion());

        Optional<Usuario> usuarioOptional = usuarioRepository.findById(delitoDTORequest.getUsuarioId());
        if (!usuarioOptional.isPresent()){
            return null;
        }
        delito.setUsuario(usuarioOptional.get());
        delito = delitoRepository.save(delito);

        return DelitoDTO.builder().id(delito.getId()).nombre(delito.getNombre()).descripcion(delito.getDescripcion()).build();
    }

    @Override
    public void borrarDelitoPorId(Long id) {

    }
}
