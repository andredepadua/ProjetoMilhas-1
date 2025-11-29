package com.Web.Plamilhas.Service.impl;

import com.Web.Plamilhas.Entity.ProgramaEntity;
import com.Web.Plamilhas.Repository.ProgramaRepository;
import com.Web.Plamilhas.Service.ProgramaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProgramaServiceImpl implements ProgramaService {

    private final ProgramaRepository repository;

    @Override
    public ProgramaEntity criar(ProgramaEntity programa) {
        // Define a data de criação se não estiver definida
        if (programa.getCriadoEm() == null) {
            programa.setCriadoEm(OffsetDateTime.now());
        }
        return repository.save(programa);
    }

    @Override
    public List<ProgramaEntity> listarTodos() {
        return repository.findAll();
    }
}