package com.Web.Plamilhas.Service.impl;

import com.Web.Plamilhas.Exception.StorageException;
import com.Web.Plamilhas.Service.StorageService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.Web.Plamilhas.Exception.ResourceNotFoundException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileSystemStorageServiceImpl implements StorageService {

    @Value("${storage.location:uploads}") // Configura o diretório de uploads (ajuste no application.properties)
    private String storageLocation;

    private Path rootLocation;

    @PostConstruct
    @Override
    public void init() {
        this.rootLocation = Paths.get(storageLocation);
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new StorageException("Não foi possível inicializar o local de armazenamento", e);
        }
    }

    @Override
    public String store(MultipartFile file, UUID compraId) {
        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        // Nome padronizado: UUID_da_Compra.extensao
        String filename = compraId.toString() + extension; 

        try {
            if (file.isEmpty()) {
                throw new StorageException("Falha ao armazenar arquivo vazio " + originalFilename);
            }
            
            Path destinationFile = this.rootLocation.resolve(Paths.get(filename))
                    .normalize().toAbsolutePath();

            if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
                // Prevenção de "path traversal"
                throw new StorageException("Não pode armazenar arquivo fora do diretório atual.");
            }

            try (var inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
            }
            return filename; // Retorna o nome/caminho para salvar no BD
        } catch (IOException e) {
            throw new StorageException("Falha ao armazenar o arquivo " + originalFilename, e);
        }
    }

    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path file = rootLocation.resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new ResourceNotFoundException("Arquivo não encontrado: " + filename);
            }
        } catch (MalformedURLException e) {
            throw new ResourceNotFoundException("Arquivo não encontrado: " + filename);
        }
    }

    @Override
    public void delete(String filename) {
        // Implementar a lógica de exclusão real
        try {
             Path file = rootLocation.resolve(filename);
             Files.deleteIfExists(file);
        } catch (IOException e) {
            // Logar o erro, mas não impedir a transação principal, se a deleção do BD for mais importante
        }
    }
}