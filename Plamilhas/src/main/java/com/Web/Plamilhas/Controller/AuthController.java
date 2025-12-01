package com.Web.Plamilhas.Controller;


import org.springframework.http.ResponseEntity;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import com.Web.Plamilhas.DTO.UsuarioLoginDTO;
import com.Web.Plamilhas.DTO.UsuarioRegisterDTO;
import com.Web.Plamilhas.DTO.UsuarioResponseDTO;
import com.Web.Plamilhas.Entity.UsuarioEntity;
import com.Web.Plamilhas.Repository.UsuarioRepository;
import com.Web.Plamilhas.Security.JwtTokenProvider;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UsuarioRepository repo;
    private final JwtTokenProvider jwt;
    private final PasswordEncoder encoder;
    

    public AuthController(UsuarioRepository repo, JwtTokenProvider jwt, PasswordEncoder encoder) {
        this.repo = repo;
        this.jwt = jwt;
        this.encoder = encoder;
    }


        @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UsuarioRegisterDTO dto) {
        if(repo.findByEmail(dto.getEmail()).isPresent()){
            return ResponseEntity
            .badRequest()
            .body(Map.of("erro", "Email já cadastrado"));
        }   

        UsuarioEntity user = new UsuarioEntity();
        user.setNomeCompleto(dto.getNomeCompleto());
        user.setEmail(dto.getEmail());
        user.setSenhaHash(encoder.encode(dto.getSenha()));

        UsuarioEntity salvo = repo.save(user);
        
        return ResponseEntity.ok(UsuarioResponseDTO.builder()
            .id(salvo.getId())
            .nomeCompleto(salvo.getNomeCompleto())
            .email(salvo.getEmail())
            .ativo(salvo.isAtivo())
            .build());
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UsuarioLoginDTO dto) {
        var opt = repo.findByEmail(dto.getEmail());
        if (opt.isEmpty()) {
            return ResponseEntity.status(401).body(Map.of("erro", "Usuário não encontrado"));
        }
        UsuarioEntity usuario = opt.get();
        if (!encoder.matches(dto.getSenha(), usuario.getSenhaHash())) {
            return ResponseEntity.status(401).body(Map.of("erro", "Senha incorreta"));
        }

        String token = jwt.gerarToken(dto.getEmail());
        return ResponseEntity.ok(Map.of("token", token,
            "id", usuario.getId(),
            "email", usuario.getEmail()
        ));
    }
}
