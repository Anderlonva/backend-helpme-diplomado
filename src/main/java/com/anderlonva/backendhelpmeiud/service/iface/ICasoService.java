package com.anderlonva.backendhelpmeiud.service.iface;

import com.anderlonva.backendhelpmeiud.dto.response.CasoDTO;
import com.anderlonva.backendhelpmeiud.exceptions.BadRequestException;
import com.anderlonva.backendhelpmeiud.model.Caso;

import java.util.List;

public interface ICasoService {

    List<CasoDTO> consultarTodos();

    Caso crear(CasoDTO casoDTO) throws BadRequestException;

    Boolean visible(Boolean visible, Long id);

    CasoDTO consultarPorId(Long id);
}
