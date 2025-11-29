package com.Web.Plamilhas.Controller;

import com.Web.Plamilhas.Entity.CartaoUsuarioEntity;
import com.Web.Plamilhas.Service.CartaoUsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/cartoes")
@RequiredArgsConstructor
public class CartaoUsuarioController {

    private final CartaoUsuarioService service;

    @PostMapping
    public ResponseEntity<CartaoUsuarioEntity> criarCartao(@RequestBody CartaoUsuarioEntity cartao) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(cartao));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CartaoUsuarioEntity> buscarPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<CartaoUsuarioEntity>> listarPorUsuario(@PathVariable UUID usuarioId) {
        return ResponseEntity.ok(service.listarPorUsuario(usuarioId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CartaoUsuarioEntity> atualizarCartao(@PathVariable UUID id, @RequestBody CartaoUsuarioEntity cartao) {
        return ResponseEntity.ok(service.atualizar(id, cartao));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarCartao(@PathVariable UUID id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
