
package com.Web.Plamilhas.Controller;

import com.Web.Plamilhas.Entity.ResgateEntity;
import com.Web.Plamilhas.Service.ResgateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/resgates")
@RequiredArgsConstructor
public class ResgateController {

    private final ResgateService resgateService;

    @PostMapping
    public ResponseEntity<ResgateEntity> registrarResgate(@RequestBody ResgateEntity resgate) {
        // Chama o serviço para registrar o resgate e efetuar o débito de pontos
        ResgateEntity resgateSalvo = resgateService.registrarResgate(resgate);
        return ResponseEntity.ok(resgateSalvo);
    }
    
    // Método GET opcional para listar resgates de um usuário específico (se necessário)
    /*
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<ResgateEntity>> listarResgatesPorUsuario(@PathVariable UUID usuarioId) {
        return ResponseEntity.ok(resgateService.listarPorUsuario(usuarioId));
    }
    */
}


