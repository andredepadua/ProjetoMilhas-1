package com.Web.Plamilhas.Service;

import com.Web.Plamilhas.DTO.CartaoDTO;
import com.Web.Plamilhas.Entity.CartaoUsuarioEntity;
import com.Web.Plamilhas.Repository.CartaoUsuarioRepository;
import com.Web.Plamilhas.Repository.UsuarioRepository;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class CartaoUsuarioService {

    private final CartaoUsuarioRepository repo;
    private final UsuarioRepository usuarioRepo;
   
    public CartaoUsuarioService(
        CartaoUsuarioRepository repo,
        UsuarioRepository usuarioRepo
        
    ) {
        this.repo = repo;
        this.usuarioRepo = usuarioRepo;
    }
       

    public CartaoUsuarioEntity criar(CartaoDTO dto) {

        CartaoUsuarioEntity c = CartaoUsuarioEntity.builder()
                .numeroCartao(dto.getNumeroCartao())
                .bandeira(dto.getBandeira())
                .usuario(usuarioRepo.findById(dto.getUsuarioId()).orElseThrow())
                .pontosPorReal(dto.getPontosPorReal())
                .build();

        return repo.save(c);
    }

    public List<CartaoUsuarioEntity> listarPorUsuario(UUID id) {
        return repo.findByUsuarioId(id);
    }
}
