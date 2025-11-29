package com.Web.Plamilhas.Entity;

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
@Table(name = "historico_pontos")
@Data @Builder @NoArgsConstructor @AllArgsConstructor

public class HistoricoPontosEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne @JoinColumn(name = "usuario_id")
    private UsuarioEntity usuario;

    @ManyToOne @JoinColumn(name = "programa_id")
private ProgramaEntity programa;
    
    private Integer variacao;
    private Long saldoApos;
    private String origem;
    private UUID origemId;
    private OffsetDateTime criadoEm;
    private String observacoes;


}
