// backend/src/main/java/com/example/projetofinal/controller/ProjetoController.java
package com.example.projetofinal.controller;

import com.example.projetofinal.model.dto.*;
import com.example.projetofinal.service.ProjetoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/projetos")
public class ProjetoController {

    private final ProjetoService projetoService;

    public ProjetoController(ProjetoService projetoService) {
        this.projetoService = projetoService;
    }

    @GetMapping
    public ResponseEntity<List<ProjetoViewDTO>> getAllProjects() {
        return ResponseEntity.ok(projetoService.findAll());
    }

    @GetMapping("/nao-avaliados")
    public ResponseEntity<List<ProjetoViewDTO>> getUnevaluatedProjects() {
        return ResponseEntity.ok(projetoService.findUnevaluated());
    }

    // Outros endpoints para POST, PUT, DELETE...
}