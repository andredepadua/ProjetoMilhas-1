package com.Web.Plamilhas.Controller;

import com.Web.Plamilhas.Entity.SaldoPontosEntity;
import com.Web.Plamilhas.Service.SaldoPontosService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/saldos")
@RequiredArgsConstructor
public class SaldoPontosController {

    private final SaldoPontosService service;

    @GetMapping("/{id}")
    public ResponseEntity<SaldoPontosEntity> buscarPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<SaldoPontosEntity>> listarPorUsuario(@PathVariable UUID usuarioId) {
        return ResponseEntity.ok(service.listarPorUsuario(usuarioId));
    }
    
    @GetMapping("/usuario/{usuarioId}/programa/{programaId}")
    public ResponseEntity<SaldoPontosEntity> buscarPorUsuarioEPrograma(
            @PathVariable UUID usuarioId,
            @PathVariable Integer programaId) {
        return ResponseEntity.ok(service.buscarPorUsuarioEPrograma(usuarioId, programaId));
    }
}