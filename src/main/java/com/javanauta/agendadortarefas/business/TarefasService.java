package com.javanauta.agendadortarefas.business;


import com.javanauta.agendadortarefas.business.dto.TarefasDTO;
import com.javanauta.agendadortarefas.business.mapper.TarefaConverter;
import com.javanauta.agendadortarefas.infraestructure.StatusNotificacao;
import com.javanauta.agendadortarefas.infraestructure.entity.TarefasEntity;
import com.javanauta.agendadortarefas.infraestructure.repository.TarefasRepository;
import com.javanauta.agendadortarefas.infraestructure.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TarefasService {


    private final TarefasRepository tarefasRepository;

    private final TarefaConverter tarefasConverter;

    private final JwtUtil jwtUtil;

    public TarefasService(TarefasRepository tarefasRepository, TarefaConverter tarefasConverter, JwtUtil jwtUtil) {
        this.tarefasRepository = tarefasRepository;
        this.tarefasConverter = tarefasConverter;
        this.jwtUtil = jwtUtil;
    }


    public TarefasDTO gravarTarefa(String token, TarefasDTO dto) {

        String email = jwtUtil.extrairEmailToken(token.substring(7));
        dto.setDataCriacao(LocalDateTime.now());
        dto.setStatusNotificacao(StatusNotificacao.PENDENTE);
        dto.setEmailUsuario(email);

        TarefasEntity entity = tarefasConverter.paraTarefaEntity(dto);

        return tarefasConverter.paraTarefasDTO(tarefasRepository.save(entity));
    }


}
