package com.Web.Plamilhas.Service;

import com.Web.Plamilhas.Entity.ProgramaEntity;
import java.util.List;

public interface ProgramaService {
    
    // Método principal para criação
    ProgramaEntity criar(ProgramaEntity programa);

    // Método para listar (útil para verificar o ID após a criação)
    List<ProgramaEntity> listarTodos();
}