package com.Web.Plamilhas.Entity;

import java.time.OffsetDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "cartao_usuario")
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class CartaoUsuarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    // Relacionamento (deve existir para vincular ao usuário)
    @ManyToOne 
    @JoinColumn(name = "usuario_id")
    private UsuarioEntity usuario; 

    // 1. CAMPO FALTANDO QUE GERA O getNome()
    @Column(nullable = false)
    private String nome; // Apelido ou nome do cartão (Ex: "Meu Visa")

    // 2. CAMPO FALTANDO QUE GERA O getNumeroFinal()
    @Column(name = "numero_final", length = 4, nullable = false)
    private String numeroFinal; // Apenas os 4 últimos dígitos (para segurança)
    
    private String bandeira; // Visa, Master, Elo, etc.

    // Relacionamento (deve existir para vincular ao programa de pontos)
    @ManyToOne 
    @JoinColumn(name = "programa_id")
    private ProgramaEntity programa;

    private OffsetDateTime criadoEm;
    private OffsetDateTime atualizadoEm;

    // ... getters e setters são gerados pelo @Data
}




/*package com.Web.Plamilhas.Entity;

import java.time.OffsetDateTime;
import java.util.UUID;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cartao_usuario")
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class CartaoUsuarioEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @ManyToOne @JoinColumn(name =  "usuario_id")
    private UsuarioEntity usuario;

    @ManyToOne @JoinColumn(name = "bandeira_id")
    private BandeiraEntity bandeira;

    @ManyToOne @JoinColumn(name = "programa_id")
    private ProgramaEntity programa;

    private String apelido;
    private String ultimos4;
    private String binPrefixo;
    private Boolean ativo;
    private OffsetDateTime criadoEm;

}
*/