package com.Web.Plamilhas.Service.impl;

import com.Web.Plamilhas.Entity.ResgateEntity;
import com.Web.Plamilhas.Enums.OrigemPontos;
import com.Web.Plamilhas.Exception.ResourceNotFoundException;
import com.Web.Plamilhas.Repository.ResgateRepository;
import com.Web.Plamilhas.Service.PontoService;
import com.Web.Plamilhas.Service.ResgateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ResgateServiceImpl implements ResgateService {

    private final ResgateRepository resgateRepository;
    private final PontoService pontoService; // Injecção do serviço de débito/crédito

    @Override
    @Transactional // Garante que salvar o resgate e o débito de pontos sejam atômicos
    public ResgateEntity registrarResgate(ResgateEntity resgate) {

        // 1. Validações iniciais (garantir que usuário, programa e pontos não sejam nulos)
        if (resgate.getUsuario() == null || resgate.getUsuario().getId() == null) {
            throw new ResourceNotFoundException("Usuário do resgate é obrigatório.");
        }
        if (resgate.getPrograma() == null || resgate.getPrograma().getId() == null) {
            throw new ResourceNotFoundException("Programa de pontos do resgate é obrigatório.");
        }
        if (resgate.getPontosUsados() == null || resgate.getPontosUsados() <= 0) {
            throw new IllegalArgumentException("Pontos usados no resgate devem ser um valor positivo.");
        }

        // 2. Preenchimento de dados e salva o ResgateEntity
        if (resgate.getDataResgate() == null) {
             resgate.setDataResgate(OffsetDateTime.now());
        }
        if (resgate.getStatus() == null || resgate.getStatus().isEmpty()) {
            resgate.setStatus("CONCLUIDO"); // Exemplo de status inicial
        }
        
        ResgateEntity resgateSalvo = resgateRepository.save(resgate);

        // 3. Efetua o débito de pontos no saldo do usuário
        // A variação é convertida para negativo (débito)
        int pontosVariacao = -1 * resgateSalvo.getPontosUsados().intValue();
        
        // Chamada ao PontoService para atualizar o saldo e registrar no histórico
        pontoService.registrarTransacao(
            resgateSalvo.getUsuario().getId(), 
            resgateSalvo.getPrograma().getId(), 
            pontosVariacao, // Valor NEGATIVO para débito
            OrigemPontos.RESGATE, 
            resgateSalvo.getId(), 
            "Resgate de " + resgateSalvo.getPontosUsados() + " pontos: " + resgateSalvo.getDescricao()
        );

        return resgateSalvo;
    }

    @Override
    public List<ResgateEntity> listarPorUsuario(UUID usuarioId) {
        return resgateRepository.findByUsuarioId(usuarioId);
    }
}