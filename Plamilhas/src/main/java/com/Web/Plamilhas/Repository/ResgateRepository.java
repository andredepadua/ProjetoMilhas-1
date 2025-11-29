package com.Web.Plamilhas.Repository;

import com.Web.Plamilhas.Entity.ResgateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ResgateRepository extends JpaRepository<ResgateEntity, UUID> {
    
    /**
     * Busca todos os registros de resgate associados a um usuário específico.
     * @param usuarioId O ID do usuário.
     * @return Uma lista de ResgateEntity.
     */
    List<ResgateEntity> findByUsuarioId(UUID usuarioId);
}
