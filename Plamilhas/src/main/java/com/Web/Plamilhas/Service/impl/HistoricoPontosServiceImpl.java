package com.Web.Plamilhas.Service.impl;

import com.Web.Plamilhas.Entity.HistoricoPontosEntity;
import com.Web.Plamilhas.Exception.ResourceNotFoundException;
import com.Web.Plamilhas.Repository.HistoricoPontosRepository;
import com.Web.Plamilhas.Service.HistoricoPontosService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HistoricoPontosServiceImpl implements HistoricoPontosService {

    private final HistoricoPontosRepository repository;
    
    @Override
    public HistoricoPontosEntity buscarPorId(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Histórico de pontos não encontrado com ID: " + id));
    }

    @Override
    public List<HistoricoPontosEntity> listarPorUsuario(UUID usuarioId) {
        return repository.findByUsuarioId(usuarioId);
    }
    
    @Override
    public List<HistoricoPontosEntity> listarPorUsuarioEPrograma(UUID usuarioId, Integer programaId) {
        return repository.findByUsuarioIdAndProgramaId(usuarioId, programaId);
    }
}