package com.Web.Plamilhas.Controller;

import com.Web.Plamilhas.Entity.ProgramaEntity;
import com.Web.Plamilhas.Service.ProgramaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/programas")
@RequiredArgsConstructor
public class ProgramaController {

    private final ProgramaService service;

    @PostMapping
    public ResponseEntity<ProgramaEntity> criarPrograma(@RequestBody ProgramaEntity programa) {
        // Chama o serviço para criar e salvar o programa no banco
        return ResponseEntity.ok(service.criar(programa));
    }

    @GetMapping
    public List<ProgramaEntity> listarProgramas() {
        return service.listarTodos();
    }
}