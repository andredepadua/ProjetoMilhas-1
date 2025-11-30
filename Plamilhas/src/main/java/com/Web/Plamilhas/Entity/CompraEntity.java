package com.Web.Plamilhas.Entity;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

import com.Web.Plamilhas.Enums.StatusPontos;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "compra")
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class CompraEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne @JoinColumn(name= "usuario_id")
    private UsuarioEntity usuario;

    @ManyToOne @JoinColumn(name = "cartao_usuario_id")
    private CartaoUsuarioEntity cartao;

    @ManyToOne @JoinColumn(name = "programa_id")
    private ProgramaEntity programa;
    
    @Column(name = "caminho_comprovante")
    private String caminhoComprovante;



    private Double valor;
    private String moeda;
    private LocalDate dataCompra;
    private OffsetDateTime registradaEm;
    private Long pontosEsperados;
    private Long pontosCalculados;

    @Enumerated(EnumType.STRING)
    private StatusPontos statusPontos;

    private LocalDate dataPrevistaCredito;
    private OffsetDateTime dataCreditado;
    private String observcoes;
    private OffsetDateTime atualizadoEm;
    private OffsetDateTime criadoEm;


}
