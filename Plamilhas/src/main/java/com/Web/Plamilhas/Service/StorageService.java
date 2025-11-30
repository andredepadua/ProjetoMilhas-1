package com.Web.Plamilhas.Service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;
import java.nio.file.Path;
import java.util.UUID;

public interface StorageService {
    
    /** Armazena o arquivo no sistema de armazenamento, usando o ID da compra como parte do nome. */
    String store(MultipartFile file, UUID compraId);

    /** Carrega um arquivo como um recurso para download. */
    Resource loadAsResource(String filename);
    
    /** Exclui um arquivo do sistema de armazenamento. */
    void delete(String filename);
    
    /** Inicializa o diretório de armazenamento. */
    void init();
}