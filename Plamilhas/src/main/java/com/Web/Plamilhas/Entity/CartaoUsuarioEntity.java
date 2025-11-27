package com.Web.Plamilhas.Entity;

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
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String numeroCartao;

    @Column(nullable = false)
    private String bandeira; // Visa, Mastercard…

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private UsuarioEntity usuario;

   
    // ex: 1 ponto por real
    private double pontosPorReal = 1.0;
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