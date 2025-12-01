package com.Web.Plamilhas.DTO;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Web.Plamilhas.Entity.UsuarioEntity;
import com.Web.Plamilhas.Security.UsuarioPrincipal;
import com.Web.Plamilhas.Service.UsuarioService;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService service;

    public UsuarioController(UsuarioService service){
        this.service = service;
    }

    @GetMapping
    public List<UsuarioEntity> listar(){
        return service.listarTodos();
    }

    @PostMapping
    public ResponseEntity<UsuarioEntity> criar(@RequestBody UsuarioEntity usuario){
        return ResponseEntity.ok(service.criar(usuario));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioEntity> buscar (@PathVariable UUID id){
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioEntity> atualizar(@PathVariable UUID id, @RequestBody UsuarioEntity usuario){
        return ResponseEntity.ok(service.atualizar(id, usuario));
    }
    @GetMapping("/me")
        public ResponseEntity<UsuarioResponseDTO> getUsuarioLogado(@AuthenticationPrincipal UsuarioPrincipal principal) {

    
    UsuarioEntity usuario = principal.getUsuario();

    return ResponseEntity.ok(
        UsuarioResponseDTO.builder()
            .id(usuario.getId())
            .email(usuario.getEmail())
            .nomeCompleto(usuario.getNomeCompleto())
            .ativo(usuario.isAtivo())
            .build()
    );

    /*if (usuario == null) {
        return ResponseEntity.status(401).build();
    }

    UsuarioResponseDTO dto = UsuarioResponseDTO.builder()
            .id(usuario.getId())
            .nomeCompleto(usuario.getNomeCompleto())
            .email(usuario.getEmail())
            .ativo(usuario.isAtivo())
            .build();

    return ResponseEntity.ok(dto);
}*/

}
}
