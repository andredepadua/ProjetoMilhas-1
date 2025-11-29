package com.Web.Plamilhas.Service;

import com.Web.Plamilhas.Entity.SaldoPontosEntity;
import java.util.List;
import java.util.UUID;

public interface SaldoPontosService {
    
    SaldoPontosEntity buscarPorId(UUID id);
    
    List<SaldoPontosEntity> listarPorUsuario(UUID usuarioId);

    SaldoPontosEntity buscarPorUsuarioEPrograma(UUID usuarioId, Integer programaId);
}