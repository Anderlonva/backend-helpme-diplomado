package com.anderlonva.backendhelpmeiud.service.iface;

import com.anderlonva.backendhelpmeiud.dto.request.DelitoDTORequest;
import com.anderlonva.backendhelpmeiud.dto.response.DelitoDTO;
import com.anderlonva.backendhelpmeiud.exceptions.BadRequestException;

import java.util.List;

public interface IDelitoService {

   List<DelitoDTO> consultarTodos();

   DelitoDTO consultarPorId(Long id);

   DelitoDTO guardarDelito(DelitoDTORequest delitoDTORequest) throws BadRequestException;

   void borrarDelitoPorId(Long id);
}
