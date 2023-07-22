package com.anderlonva.backendhelpmeiud.service.impl;

import com.anderlonva.backendhelpmeiud.dto.response.CasoDTO;
import com.anderlonva.backendhelpmeiud.exceptions.BadRequestException;
import com.anderlonva.backendhelpmeiud.exceptions.ErrorDto;
import com.anderlonva.backendhelpmeiud.model.Caso;
import com.anderlonva.backendhelpmeiud.model.Delito;
import com.anderlonva.backendhelpmeiud.model.Usuario;
import com.anderlonva.backendhelpmeiud.repository.ICasoRepository;
import com.anderlonva.backendhelpmeiud.repository.IDelitoRepository;
import com.anderlonva.backendhelpmeiud.repository.IUsuarioRepository;
import com.anderlonva.backendhelpmeiud.service.iface.ICasoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j // sirve para console.log
public class CasoServiceImpl implements ICasoService {

    @Autowired
    private ICasoRepository casoRepository;

    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Autowired
    private IDelitoRepository delitoRepository;


    @Override
    @Transactional(readOnly = true)
    public List<CasoDTO> consultarTodos() {
        List<Caso> casos = casoRepository.findAll();
        /* List<CasoDTO> casosDTOS = new ArrayList<>(); // programacion imperativa

        for(Caso caso : casos){
            CasoDTO casoDTO = CasoDTO.builder().id(caso.getId()).fechaHora(caso.getFechaHora()).descripcion(caso.getDescripcion()).altitud(caso.getAltitud())
                    .latitud(caso.getLatitud()).longitud(caso.getLongitud()).rmiUrl(caso.getRmiUrl()).urlMap(caso.getUrlMap()).esVisible(caso.getEsVisible())
                    .usuarioId(caso.getUsuario().getId()).delitoId(caso.getDelito().getId()).build();

            casosDTOS.add(casoDTO);
        }*/
        // programacion funcional
        List<CasoDTO> casosDTOS = casos.stream().map( caso -> {
           return CasoDTO.builder().id(caso.getId()).fechaHora(caso.getFechaHora()).descripcion(caso.getDescripcion()).altitud(caso.getAltitud())
                    .latitud(caso.getLatitud()).longitud(caso.getLongitud()).rmiUrl(caso.getRmiUrl()).urlMap(caso.getUrlMap()).esVisible(caso.getEsVisible())
                    .usuarioId(caso.getUsuario().getId()).delitoId(caso.getDelito().getId()).build();
        }).collect(Collectors.toList());

        return casosDTOS;
    }



    @Override
    @Transactional
    public Caso crear(CasoDTO casoDTO) throws BadRequestException {

        Optional<Usuario> usuario = usuarioRepository.findById(casoDTO.getUsuarioId());
        Optional<Delito> delito = delitoRepository.findById(casoDTO.getDelitoId());

        if (!usuario.isPresent() || !delito.isPresent()){
            log.error("No existe usuario {} ", casoDTO.getUsuarioId());
            log.error("No existe delito {} ", casoDTO.getDelitoId());
            throw new BadRequestException(
                    ErrorDto.builder()
                            .status(HttpStatus.BAD_REQUEST.value()).message("no existe usuario o delito").
                            error(HttpStatus.BAD_REQUEST.getReasonPhrase()).build()
            );
        }


        Caso caso = new Caso();
        caso.setFechaHora(casoDTO.getFechaHora());
        caso.setLatitud(casoDTO.getLatitud());
        caso.setAltitud(casoDTO.getAltitud());
        caso.setLongitud(casoDTO.getLongitud());
        caso.setDescripcion(casoDTO.getDescripcion());
        caso.setEsVisible(true);
        caso.setUrlMap(casoDTO.getUrlMap());
        caso.setRmiUrl(casoDTO.getRmiUrl());
        caso.setUsuario(usuario.get());
        caso.setDelito(delito.get());


        return casoRepository.save(caso);

    }

    @Override
    @Transactional
    public Boolean visible(Boolean visible, Long id) {
        return casoRepository.setVisible(visible, id);
    }

    @Override
    @Transactional(readOnly = true)
    public CasoDTO consultarPorId(Long id) {
        Optional<Caso> casoOptional = casoRepository.findById(id);
        if (casoOptional.isPresent()){
            Caso caso = casoOptional.get();

            return CasoDTO.builder().id(caso.getId()).fechaHora(caso.getFechaHora()).descripcion(caso.getDescripcion()).altitud(caso.getAltitud())
                    .latitud(caso.getLatitud()).longitud(caso.getLongitud()).rmiUrl(caso.getRmiUrl()).urlMap(caso.getUrlMap()).esVisible(caso.getEsVisible())
                    .usuarioId(caso.getUsuario().getId()).delitoId(caso.getDelito().getId()).build();
        }

        log.warn("No existe usuario {} " , id);
        return null;
    }

}
