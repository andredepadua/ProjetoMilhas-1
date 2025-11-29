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
@Table(name = "saldo_pontos")
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class SaldoPontosEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne @JoinColumn(name = "usuario_id")
    private UsuarioEntity usuario;

    @ManyToOne @JoinColumn(name = "programa_id")
    private ProgramaEntity programa;
    

    private Long saldo;
    private Long saldoPendente;
    private OffsetDateTime atualizadoEm;

}
