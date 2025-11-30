package com.Web.Plamilhas.Service.impl;

import com.Web.Plamilhas.Entity.CompraEntity;
import com.Web.Plamilhas.Enums.OrigemPontos;
import com.Web.Plamilhas.Exception.ResourceNotFoundException;
import com.Web.Plamilhas.Repository.CompraRepository;
import com.Web.Plamilhas.Service.CompraService;
import com.Web.Plamilhas.Service.PontoService; 
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor // Injeta automaticamente os final fields (repo e pontoService)
public class CompraServiceImpl implements CompraService {

    private final CompraRepository compraRepository;
    private final PontoService pontoService; 

    @Override
    @Transactional
    public CompraEntity registrarCompra(CompraEntity compra) {
        // 1. Salvar a Compra primeiro para obter o ID (e garantir o registro)
        CompraEntity compraSalva = compraRepository.save(compra);
        
        Long pontosEsperadosLong = compraSalva.getPontosEsperados();
        Integer variacaoPontos = (pontosEsperadosLong != null) ? pontosEsperadosLong.intValue() : 0;
        
        // 2. Se a compra gera pontos, registra a transação
        if (variacaoPontos > 0) {
            pontoService.registrarTransacao(
                compraSalva.getUsuario().getId(), 
                compraSalva.getPrograma().getId(), 
                variacaoPontos, 
                OrigemPontos.COMPRA, // Usando o ENUM que você definiu
                compraSalva.getId(), 
                "Pontos esperados da compra registrada em " + compraSalva.getDataCompra()
            );
        }

        return compraSalva;
    }
    // CORREÇÃO 1: Implementação do método criar(CompraEntity)
    @Override
    public CompraEntity criar(CompraEntity compra) {
        compra.setCriadoEm(OffsetDateTime.now());
        compra.setAtualizadoEm(OffsetDateTime.now());
        
        // 1. Salva a compra no banco de dados
        CompraEntity compraSalva = compraRepository.save(compra);
        
        // 2. Registra e calcula os pontos através do PontoService
        // O PontoService deve usar o valor da compra e pontosPorReal do cartão 
        // para calcular o saldo e criar o HistoricoPontosEntity.
        pontoService.creditarPontos(
            compraSalva.getUsuario().getId(), 
            compraSalva.getPrograma().getId(), 
            compraSalva.getValor(), 
            compraSalva.getCartao().getPontosPorReal(),
            compraSalva.getId()
        );
        
        return compraSalva;
    }

    // CORREÇÃO 2: Implementação do método listarTodas()
    @Override
    public List<CompraEntity> listarTodas() {
        return compraRepository.findAll();
    }

    // Método para buscar por ID (necessário para os Controllers)
    @Override
    public CompraEntity buscarPorId(UUID id) {
        return compraRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Compra não encontrada com ID: " + id));
    }

    // Método para atualizar (usado para salvar o caminho do comprovante)
    @Override
    public CompraEntity atualizar(UUID id, CompraEntity compraDetalhes) {
        CompraEntity compraExistente = buscarPorId(id);
        
        // Atualiza apenas o caminho do comprovante se foi fornecido
        if (compraDetalhes.getCaminhoComprovante() != null) {
            compraExistente.setCaminhoComprovante(compraDetalhes.getCaminhoComprovante());
        }

        compraExistente.setAtualizadoEm(OffsetDateTime.now());
        return compraRepository.save(compraExistente);
    }
    



// Método para deletar (com reversão de pontos)
    @Override
    public void deletar(UUID id) {
        // 1. Busca a compra para reverter a transação
        CompraEntity compra = buscarPorId(id);
        
        // 2. Reverte os pontos no PontoService
        pontoService.reverterCredito(
            compra.getUsuario().getId(), 
            compra.getPrograma().getId(), 
            compra.getId()
        );
        
        // 3. Deleta o registro no banco de dados
        compraRepository.deleteById(id);
    }
    
    @Override
    public List<CompraEntity> listarPorUsuario(UUID usuarioId) {
        return compraRepository.findByUsuarioId(usuarioId);
    }
}