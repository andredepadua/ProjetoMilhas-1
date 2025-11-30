package com.Web.Plamilhas.Service;

import com.Web.Plamilhas.Entity.HistoricoPontosEntity;
import com.Web.Plamilhas.Enums.OrigemPontos;
import java.util.UUID;

public interface PontoService {
    /**
 * Calcula e credita pontos no saldo do usuário e registra no histórico.
 * @param usuarioId ID do usuário
 * @param programaId ID do programa de pontos
 * @param valorGasto Valor monetário da compra
 * @param pontosPorReal Fator de conversão do cartão
 * @param referenciaId ID da Compra que gerou o crédito
 */
void creditarPontos(UUID usuarioId, Integer programaId, Double valorGasto, Double pontosPorReal, UUID referenciaId);
    
    /**
     * Registra uma transação de pontos (crédito ou débito) e atualiza o saldo do usuário.
     * @param usuarioId ID do usuário.
     * @param programaId ID do programa de pontos.
     * @param variacao A quantidade de pontos (positivo para crédito, negativo para débito).
     * @param origem A origem da transação (Ex: COMPRA, RESGATE).
     * @param origemId O ID da entidade de origem (Ex: CompraEntity.id, ResgateEntity.id).
     * @param observacoes Uma descrição opcional.
     * @return A entidade HistoricoPontosEntity criada.
     */
    HistoricoPontosEntity registrarTransacao(
        UUID usuarioId, 
        Integer programaId, 
        Integer variacao, 
        OrigemPontos origem, 
        UUID origemId, 
        String observacoes
    );
    void reverterCredito(UUID usuarioId, Integer programaId, UUID referenciaId);
}