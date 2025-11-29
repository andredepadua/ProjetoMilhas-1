package com.Web.Plamilhas.Service.impl;

import com.Web.Plamilhas.Entity.SaldoPontosEntity;
import com.Web.Plamilhas.Exception.ResourceNotFoundException;
import com.Web.Plamilhas.Repository.SaldoPontosRepository;
import com.Web.Plamilhas.Service.SaldoPontosService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SaldoPontosServiceImpl implements SaldoPontosService {

    private final SaldoPontosRepository repository;
    
    @Override
    public SaldoPontosEntity buscarPorId(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Saldo de pontos não encontrado com ID: " + id));
    }

    @Override
    public List<SaldoPontosEntity> listarPorUsuario(UUID usuarioId) {
        return repository.findByUsuarioId(usuarioId);
    }
    
    @Override
    public SaldoPontosEntity buscarPorUsuarioEPrograma(UUID usuarioId, Integer programaId) {
        return repository.findByUsuarioIdAndProgramaId(usuarioId, programaId)
                .orElseThrow(() -> new ResourceNotFoundException("Saldo de pontos não encontrado para este usuário e programa."));
    }
}