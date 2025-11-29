package com.Web.Plamilhas.Service;

import com.Web.Plamilhas.Entity.ResgateEntity;
import java.util.List;
import java.util.UUID;

public interface ResgateService {
    
    /**
     * Registra um novo resgate de pontos e debita o saldo do usuário.
     * @param resgate A entidade ResgateEntity contendo os dados do resgate.
     * @return A entidade ResgateEntity registrada após a transação de pontos.
     */
    ResgateEntity registrarResgate(ResgateEntity resgate);

    List<ResgateEntity> listarPorUsuario(UUID usuarioId);
}
