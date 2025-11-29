package com.Web.Plamilhas.Repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Web.Plamilhas.Entity.SaldoPontosEntity;

@Repository
public interface SaldoPontosRepository extends JpaRepository <SaldoPontosEntity, UUID> {

    /**
     * Busca o saldo de pontos de um usuário para um programa específico.
     * @param usuarioId O ID do usuário (UUID).
     * @param programaId O ID do programa de pontos (Integer).
     * @return Um Optional contendo o SaldoPontosEntity correspondente, se encontrado.
     */
    Optional<SaldoPontosEntity> findByUsuarioIdAndProgramaId (UUID usuarioId, Integer programaId);
}
