package com.Web.Plamilhas.Service.impl;

import com.Web.Plamilhas.Entity.CartaoUsuarioEntity;
import com.Web.Plamilhas.Exception.ResourceNotFoundException;
import com.Web.Plamilhas.Repository.CartaoUsuarioRepository;
import com.Web.Plamilhas.Service.CartaoUsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CartaoUsuarioServiceImpl implements CartaoUsuarioService {

    private final CartaoUsuarioRepository repository;
    
    @Override
    @Transactional
    public CartaoUsuarioEntity criar(CartaoUsuarioEntity cartao) {
        // Inicializa o timestamp de criação
        if (cartao.getCriadoEm() == null) {
            cartao.setCriadoEm(OffsetDateTime.now());
        }
        return repository.save(cartao);
    }

    @Override
    public CartaoUsuarioEntity buscarPorId(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cartão não encontrado com ID: " + id));
    }

    @Override
    @Transactional
    public CartaoUsuarioEntity atualizar(UUID id, CartaoUsuarioEntity cartaoAtualizado) {
        CartaoUsuarioEntity cartaoExistente = buscarPorId(id);
        
        // Atualiza apenas campos fornecidos no DTO ou que podem ser alterados
        if (cartaoAtualizado.getNome() != null) {
            cartaoExistente.setNome(cartaoAtualizado.getNome());
        }
        if (cartaoAtualizado.getNumeroFinal() != null) {
            cartaoExistente.setNumeroFinal(cartaoAtualizado.getNumeroFinal());
        }
        if (cartaoAtualizado.getBandeira() != null) {
            cartaoExistente.setBandeira(cartaoAtualizado.getBandeira());
        }
        if (cartaoAtualizado.getPrograma() != null) {
            cartaoExistente.setPrograma(cartaoAtualizado.getPrograma());
        }
        
        cartaoExistente.setAtualizadoEm(OffsetDateTime.now());
        return repository.save(cartaoExistente);
    }

    @Override
    @Transactional
    public void deletar(UUID id) {
        // Uma validação para garantir que o cartão exista antes de deletar é opcional,
        // mas o deleteById do JpaRepository já é seguro.
        repository.deleteById(id);
    }

    @Override
    public List<CartaoUsuarioEntity> listarPorUsuario(UUID usuarioId) {
        return repository.findByUsuarioId(usuarioId);
    }
}
