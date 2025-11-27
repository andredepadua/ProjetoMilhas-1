package com.Web.Plamilhas.Service.impl;

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
}
