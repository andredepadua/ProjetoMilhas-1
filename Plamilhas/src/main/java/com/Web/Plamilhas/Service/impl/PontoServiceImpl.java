package com.Web.Plamilhas.Service.impl;

import com.Web.Plamilhas.Entity.HistoricoPontosEntity;
import com.Web.Plamilhas.Entity.SaldoPontosEntity;
import com.Web.Plamilhas.Entity.UsuarioEntity;
import com.Web.Plamilhas.Entity.ProgramaEntity;
import com.Web.Plamilhas.Enums.OrigemPontos;
import com.Web.Plamilhas.Exception.ResourceNotFoundException; // Sua classe de exceção
import com.Web.Plamilhas.Repository.HistoricoPontosRepository;
import com.Web.Plamilhas.Repository.SaldoPontosRepository;
import com.Web.Plamilhas.Repository.UsuarioRepository;
import com.Web.Plamilhas.Repository.ProgramaRepository;
import com.Web.Plamilhas.Service.PontoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PontoServiceImpl implements PontoService {

    private final UsuarioRepository usuarioRepository;
    private final ProgramaRepository programaRepository;
    private final SaldoPontosRepository saldoPontosRepository;
    private final HistoricoPontosRepository historicoPontosRepository;

    @Override
    @Transactional // Garante que a atualização do Saldo e do Histórico sejam atômicas
    public HistoricoPontosEntity registrarTransacao(
            UUID usuarioId,
            Integer programaId,
            Integer variacao,
            OrigemPontos origem,
            UUID origemId,
            String observacoes) {

        // 1. Busca das Entidades (Usuário e Programa)
        UsuarioEntity usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com ID: " + usuarioId));

        ProgramaEntity programa = programaRepository.findById(programaId)
                .orElseThrow(() -> new ResourceNotFoundException("Programa não encontrado com ID: " + programaId));

        // 2. Busca ou Criação do Saldo
        Optional<SaldoPontosEntity> saldoOpt = saldoPontosRepository.findByUsuarioIdAndProgramaId(usuarioId, programaId);

        SaldoPontosEntity saldoEntity;
        if (saldoOpt.isPresent()) {
            saldoEntity = saldoOpt.get();
        } else {
            // Cria um novo registro de saldo se não existir (saldo inicial 0)
            saldoEntity = SaldoPontosEntity.builder()
                    .usuario(usuario)
                    .programa(programa)
                    .saldo(0L)
                    .saldoPendente(0L)
                    .build();
        }

        // 3. Atualização do Saldo
        long novoSaldo = saldoEntity.getSaldo() + variacao;
        
        // Regra de Negócio: Não permite saldo negativo
        if (novoSaldo < 0) {
             throw new RuntimeException("Saldo insuficiente para a transação. Saldo atual: " + saldoEntity.getSaldo() + ", Variação: " + variacao);
        }

        saldoEntity.setSaldo(novoSaldo);
        saldoEntity.setAtualizadoEm(OffsetDateTime.now());
        saldoPontosRepository.save(saldoEntity);

        // 4. Criação e Salva do Histórico
        HistoricoPontosEntity historico = HistoricoPontosEntity.builder()
                .usuario(usuario)
                .programa(programa)
                .variacao(variacao)
                .saldoApos(novoSaldo)
                .origem(origem.name()) // Salva o nome do ENUM (COMPRA, RESGATE, etc.)
                .origemId(origemId)
                .observacoes(observacoes)
                .criadoEm(OffsetDateTime.now())
                .build();

        return historicoPontosRepository.save(historico);
    }
    @Override
public void reverterCredito(UUID usuarioId, Integer programaId, UUID referenciaId) {
    // NOTA: Esta é uma implementação SIMPLES para fins de compilação.
    // Em um sistema real, você buscaria o registro no Histórico pela referenciaId (ID da Compra)
    // para descobrir exatamente quantos pontos reverter.

    // 1. (Lógica Real) Busca o histórico original de crédito
    // Supondo que você tem um método para buscar histórico por referência:
    /*
    HistoricoPontosEntity historicoOriginal = historicoRepository
        .findByReferenciaId(referenciaId)
        .orElseThrow(() -> new ResourceNotFoundException("Histórico original não encontrado."));
        
    Integer pontosAReverter = historicoOriginal.getVariacao();
    */

    // 2. (Solução Temporária) Se você não tem a lógica de busca por referência no repositório,
    // apenas registra uma transação de reversão no histórico com um valor de débito (negativo).
    // Vou usar um valor placeholder (Ex: -1000) até a lógica de busca ser criada.
    
    // **Ajuste o saldo. É a parte mais importante:**
    // saldoService.ajustarSaldo(usuarioId, programaId, -pontosAReverter);
    
    // O foco agora é na compilação. Assumindo a lógica mais simples:
    
    // Se a deleção da Compra for sempre acompanhada de um ajuste manual no Histórico,
    // o PontoService precisará de mais contexto. 
    
    // Para resolver o erro de compilação imediatamente, deixe o corpo do método assim:
    System.out.println("Revertendo crédito da transação: " + referenciaId);
    
    // **Lembrete:** Você terá que implementar a lógica de débito no SaldoPontosEntity aqui.
}
@Override
public void creditarPontos(UUID usuarioId, Integer programaId, Double valorGasto, Double pontosPorReal, UUID referenciaId) {
    // 1. Calcula o total de pontos
    int pontosCreditados = (int) Math.round(valorGasto * pontosPorReal);

    // 2. **LÓGICA REAL DE NEGÓCIO (A SER IMPLEMENTADA COMPLETAMENTE):**
    //    Esta parte é a mais importante. Ela deve:
    //    a) Buscar/Criar o SaldoPontosEntity.
    //    b) Atualizar o saldo ('saldo' ou 'saldoPendente', dependendo da regra de negócio).
    //    c) Criar o registro no HistoricoPontosEntity.

    // Apenas para fins de compilação e teste:
    System.out.println("Transação de Crédito processada: " + referenciaId);
    System.out.println("Pontos a creditar: " + pontosCreditados);
    
}
}
