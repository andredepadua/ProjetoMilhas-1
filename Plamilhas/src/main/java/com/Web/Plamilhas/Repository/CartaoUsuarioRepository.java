package com.Web.Plamilhas.Repository;

import com.Web.Plamilhas.Entity.CartaoUsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CartaoUsuarioRepository extends JpaRepository<CartaoUsuarioEntity, UUID> {
    
    /**
     * Lista todos os cartões cadastrados pertencentes a um usuário específico.
     * @param usuarioId O ID do usuário.
     * @return Lista de CartaoUsuarioEntity.
     */
    List<CartaoUsuarioEntity> findByUsuarioId(UUID usuarioId);
}