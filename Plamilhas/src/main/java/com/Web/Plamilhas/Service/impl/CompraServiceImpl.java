/*package com.Web.Plamilhas.Service.impl;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.Web.Plamilhas.Entity.CompraEntity;
import com.Web.Plamilhas.Entity.CartaoUsuarioEntity;
import com.Web.Plamilhas.Entity.UsuarioEntity;
import com.Web.Plamilhas.Exception.ResourceNotFoundException;
import com.Web.Plamilhas.Repository.CartaoUsuarioRepository;
import com.Web.Plamilhas.Repository.CompraRepository;
import com.Web.Plamilhas.Repository.UsuarioRepository;
import com.Web.Plamilhas.Service.CompraService;

@Service
public class CompraServiceImpl implements CompraService {

    private final CompraRepository compraRepo;
    private final UsuarioRepository usuarioRepo;
    private final CartaoUsuarioRepository cartaoRepo;

    public CompraServiceImpl(
            CompraRepository compraRepo,
            UsuarioRepository usuarioRepo,
            CartaoUsuarioRepository cartaoRepo
    ) {
        this.compraRepo = compraRepo;
        this.usuarioRepo = usuarioRepo;
        this.cartaoRepo = cartaoRepo;
    }

    @Override
    public CompraEntity registrarCompra(CompraEntity compra) {

        // 🔥 Validar usuário
        if (compra.getUsuario() == null || compra.getUsuario().getId() == null) {
            throw new IllegalArgumentException("ID do usuário não pode ser nulo!");
        }

        UsuarioEntity usuario = usuarioRepo.findById(compra.getUsuario().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado."));

        // 🔥 Validar cartão
        if (compra.getCartao() == null || compra.getCartao().getId() == null) {
            throw new IllegalArgumentException("ID do cartão não pode ser nulo!");
        }

        CartaoUsuarioEntity cartao = cartaoRepo.findById(compra.getCartao().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Cartão não encontrado."));

        // 🔥 Atribuir entidades carregadas
        compra.setUsuario(usuario);
        compra.setCartao(cartao);

        // 🔥 Registrar data
        compra.setRegistradaEm(OffsetDateTime.now());

        return compraRepo.save(compra);
    }

    @Override
    public List<CompraEntity> listarPorUsuario(UUID usuarioId) {
        return compraRepo.findByUsuarioId(usuarioId);
    }
}*/
package com.Web.Plamilhas.Service.impl;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Web.Plamilhas.Entity.CompraEntity;
import com.Web.Plamilhas.Entity.HistoricoPontosEntity;
import com.Web.Plamilhas.Entity.SaldoPontosEntity;
import com.Web.Plamilhas.Enums.StatusPontos;
import com.Web.Plamilhas.Enums.OrigemPontos;
import com.Web.Plamilhas.Exception.ResourceNotFoundException;
import com.Web.Plamilhas.Repository.CompraRepository;
import com.Web.Plamilhas.Repository.SaldoPontosRepository;
import com.Web.Plamilhas.Repository.UsuarioRepository;
import com.Web.Plamilhas.Repository.HistoricoPontosRepository;
import com.Web.Plamilhas.Service.CompraService;

@Service
public class CompraServiceImpl implements CompraService{

    // Variáveis existentes e novas dependências
    private final CompraRepository repo;
    private final SaldoPontosRepository saldoPontosRepo; // Necessário para atualizar o saldo
    private final UsuarioRepository usuarioRepo; // Necessário para validar o usuário
    private final HistoricoPontosRepository historicoPontosRepo; // Necessário para registrar o histórico

    // Construtor atualizado com as novas dependências injetadas
    public CompraServiceImpl(
        CompraRepository repo,
        SaldoPontosRepository saldoPontosRepo,
        UsuarioRepository usuarioRepo,
        HistoricoPontosRepository historicoPontosRepo
    ){
        this.repo = repo;
        this.saldoPontosRepo = saldoPontosRepo;
        this.usuarioRepo = usuarioRepo;
        this.historicoPontosRepo = historicoPontosRepo;
    }

    @Override
    @Transactional
    public CompraEntity registrarCompra(CompraEntity compra){
        // 1. Validações iniciais e busca do Usuário para contexto de persistência
        if (compra.getUsuario() == null || compra.getUsuario().getId() == null) {
            throw new ResourceNotFoundException("Usuário da compra é obrigatório.");
        }
        if (compra.getPrograma() == null || compra.getPrograma().getId() == null) {
            throw new ResourceNotFoundException("Programa de pontos da compra é obrigatório.");
        }
        
        // Busca a entidade Usuario e garante que ela existe
        var usuario = usuarioRepo.findById(compra.getUsuario().getId())
            .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com ID: " + compra.getUsuario().getId()));
        compra.setUsuario(usuario);
        
        // 2. Lógica de Cálculo de Pontos (IMPLEMENTAÇÃO DO CÁLCULO)
        // **Este é um placeholder.** O cálculo real deve ser implementado aqui, 
        // baseado nas regras da bandeira e tipo de cartão (valor * multiplicador).
        long pontosCalculados = (long) (compra.getValor() != null ? compra.getValor() * 10 : 0); // Exemplo: 10 pontos por R$1,00
        
        // 3. Preenchimento dos dados da compra
        compra.setPontosCalculados(pontosCalculados);
        compra.setPontosEsperados(pontosCalculados); 
        compra.setStatusPontos(StatusPontos.PENDENTE); // Pontos inicialmente PENDENTE
        compra.setRegistradaEm(OffsetDateTime.now());

        // 4. Salva a CompraEntity
        CompraEntity compraSalva = repo.save(compra);
        
        // 5. Atualiza/Cria SaldoPontosEntity (adiciona ao saldo pendente)
        SaldoPontosEntity saldoPontos = saldoPontosRepo
            .findByUsuarioIdAndProgramaId(compraSalva.getUsuario().getId(), compraSalva.getPrograma().getId())
            .orElseGet(() -> SaldoPontosEntity.builder()
                .usuario(compraSalva.getUsuario())
                .programa(compraSalva.getPrograma())
                .saldo(0L)
                .saldoPendente(0L)
                .build()
            );
        
        saldoPontos.setSaldoPendente(saldoPontos.getSaldoPendente() + pontosCalculados);
        saldoPontos.setAtualizadoEm(OffsetDateTime.now());
        SaldoPontosEntity saldoSalvo = saldoPontosRepo.save(saldoPontos);
        
        // 6. Registra no Histórico de Pontos
        HistoricoPontosEntity historico = HistoricoPontosEntity.builder()
            .usuario(compraSalva.getUsuario())
            .programa(compraSalva.getPrograma())
            .variacao((int) pontosCalculados)
            // SaldoApos é a soma do saldo Creditado + Pendente
            .saldoApos(saldoSalvo.getSaldo() + saldoSalvo.getSaldoPendente())
            .origem(OrigemPontos.COMPRA.name()) // Origem COMPRA
            .origemId(compraSalva.getId())
            .criadoEm(OffsetDateTime.now())
            .observacoes("Pontos pendentes registrados por compra de valor " + compraSalva.getValor())
            .build();
            
        historicoPontosRepo.save(historico);

        return compraSalva;
    }

    @Override
    public List<CompraEntity> listarPorUsuario(UUID usuarioId){
        return repo.findByUsuarioId(usuarioId);
    }

}