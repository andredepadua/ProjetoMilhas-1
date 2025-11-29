package com.Web.Plamilhas.Service;

import com.Web.Plamilhas.Entity.HistoricoPontosEntity;
import java.util.List;
import java.util.UUID;

public interface HistoricoPontosService {
    
    HistoricoPontosEntity buscarPorId(UUID id);
    
    List<HistoricoPontosEntity> listarPorUsuario(UUID usuarioId);

    List<HistoricoPontosEntity> listarPorUsuarioEPrograma(UUID usuarioId, Integer programaId);
}
