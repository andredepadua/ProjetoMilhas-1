package com.Web.Plamilhas.Service.impl;

import com.Web.Plamilhas.Entity.CompraEntity;
import com.Web.Plamilhas.Enums.OrigemPontos;
import com.Web.Plamilhas.Repository.CompraRepository;
import com.Web.Plamilhas.Service.CompraService;
import com.Web.Plamilhas.Service.PontoService; // Importe o PontoService
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor // Injeta automaticamente os final fields (repo e pontoService)
public class CompraServiceImpl implements CompraService {

    private final CompraRepository compraRepository;
    private final PontoService pontoService; // INJEÇÃO DO NOVO SERVIÇO

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
    
    @Override
    public List<CompraEntity> listarPorUsuario(UUID usuarioId) {
        return compraRepository.findByUsuarioId(usuarioId);
    }
}