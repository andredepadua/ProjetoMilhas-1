package com.Web.Plamilhas.Service;

import java.util.List;
import java.util.UUID;

import com.Web.Plamilhas.Entity.CompraEntity;

public interface CompraService {
    CompraEntity registrarCompra(CompraEntity compra);
    List<CompraEntity> listarPorUsuario(UUID usuarioId);
    
    // Método para criação (assumindo que já existe)
    CompraEntity criar(CompraEntity compra); 
    
    // NOVO: Método para buscar a Compra por ID (necessário para upload/download)
    CompraEntity buscarPorId(UUID id); 
    
    // NOVO: Método para atualizar a Compra (necessário para salvar o caminho do comprovante)
    // Nota: É comum usar um DTO aqui, mas por simplicidade, usamos a própria Entity
    CompraEntity atualizar(UUID id, CompraEntity compraDetalhes); 

    // NOVO: Método para deletar a Compra
    void deletar(UUID id); 

    // Outros métodos de listagem que você já tenha (ex: listarPorUsuario)
    List<CompraEntity> listarTodas();

}
