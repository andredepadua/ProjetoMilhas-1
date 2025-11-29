package com.Web.Plamilhas.Controller;

import com.Web.Plamilhas.Entity.HistoricoPontosEntity;
import com.Web.Plamilhas.Service.HistoricoPontosService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/historico")
@RequiredArgsConstructor
public class HistoricoPontosController {

    private final HistoricoPontosService service;

    @GetMapping("/{id}")
    public ResponseEntity<HistoricoPontosEntity> buscarPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<HistoricoPontosEntity>> listarPorUsuario(@PathVariable UUID usuarioId) {
        return ResponseEntity.ok(service.listarPorUsuario(usuarioId));
    }
    
    @GetMapping("/usuario/{usuarioId}/programa/{programaId}")
    public ResponseEntity<List<HistoricoPontosEntity>> listarPorUsuarioEPrograma(
            @PathVariable UUID usuarioId,
            @PathVariable Integer programaId) {
        return ResponseEntity.ok(service.listarPorUsuarioEPrograma(usuarioId, programaId));
    }
}
