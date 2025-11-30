package com.Web.Plamilhas.Controller;


import java.util.List;
import java.util.UUID;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.Web.Plamilhas.Service.StorageService;
import com.Web.Plamilhas.Entity.CompraEntity;
import com.Web.Plamilhas.Service.CompraService;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.HttpHeaders; // Necessário para o download
import org.springframework.core.io.Resource; // Necessário para o download
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequestMapping("/api/compras")
public class CompraController {
    private final CompraService compraService;
    private final StorageService storageService;

    public CompraController(CompraService compraService, StorageService storageService){
        this.compraService = compraService;
        this.storageService = storageService;
    }
    @PostMapping
    public ResponseEntity<CompraEntity> registrar (@RequestBody CompraEntity compra){
        return ResponseEntity.ok(compraService.registrarCompra(compra));
    }
    @GetMapping("/usuario/{usuarioId}")
    public List<CompraEntity> listarPorUsuario (@PathVariable UUID usuarioId){
        return compraService.listarPorUsuario(usuarioId);
    }
    @PostMapping("/{compraId}/upload")
    public ResponseEntity<CompraEntity> uploadComprovante(
            @PathVariable UUID compraId,
            @RequestParam("file") MultipartFile file) {
        
        CompraEntity compra = compraService.buscarPorId(compraId);

        // Armazena o arquivo e obtém o nome/caminho
        String caminho = storageService.store(file, compraId);

        // Atualiza a entidade Compra com o caminho do comprovante
        compra.setCaminhoComprovante(caminho);
        
        // **IMPORTANTE:** Assumindo que você tem um método 'atualizar' no CompraService
        CompraEntity compraAtualizada = compraService.atualizar(compraId, compra); 

        return ResponseEntity.ok(compraAtualizada);
    }
    
    // Endpoint para download (GET)
    @GetMapping("/{compraId}/comprovante")
    public ResponseEntity<Resource> downloadComprovante(@PathVariable UUID compraId) {
        CompraEntity compra = compraService.buscarPorId(compraId);
        String filename = compra.getCaminhoComprovante();
        
        if (filename == null || filename.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        // CORREÇÃO: O tipo Resource agora é o correto
        Resource file = storageService.loadAsResource(filename);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }
    
    // Boas Práticas: Deleção da Compra deve também deletar o arquivo
    @DeleteMapping("/{compraId}")
    public ResponseEntity<Void> deletarCompra(@PathVariable UUID compraId) {
        CompraEntity compra = compraService.buscarPorId(compraId);
        
        // 1. Deleta o arquivo no storage, se ele existir
        if (compra.getCaminhoComprovante() != null) {
            storageService.delete(compra.getCaminhoComprovante());
        }
        
        // 2. Deleta a compra no banco de dados (que também reverte a transação)
        compraService.deletar(compraId); 
        
        return ResponseEntity.noContent().build();
    }
    

}
