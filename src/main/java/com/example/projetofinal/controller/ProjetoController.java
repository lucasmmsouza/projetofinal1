package com.example.projetofinal.controller;

import com.example.projetofinal.model.dto.*;
import com.example.projetofinal.service.ProjetoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/projetos")
public class ProjetoController {

    private final ProjetoService projetoService;

    public ProjetoController(ProjetoService projetoService) {
        this.projetoService = projetoService;
    }

    @GetMapping
    public ResponseEntity<List<ProjetoViewDTO>> getAllProjects(
            @RequestParam(required = false) String areaTematica) {
        return ResponseEntity.ok(projetoService.findAll(areaTematica));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjetoViewDTO> getProjectById(@PathVariable Long id) {
        return ResponseEntity.ok(projetoService.findById(id));
    }

    @GetMapping("/nao-avaliados")
    public ResponseEntity<List<ProjetoViewDTO>> getUnevaluatedProjects() {
        return ResponseEntity.ok(projetoService.findUnevaluated());
    }

    @GetMapping("/vencedores")
    public ResponseEntity<List<ProjetoVencedorDTO>> getWinningProjects() {
        return ResponseEntity.ok(projetoService.findWinningProjects());
    }

    @PostMapping
    public ResponseEntity<ProjetoViewDTO> createProject(@Valid @RequestBody ProjetoCreateDTO dto) {
        ProjetoViewDTO projetoCriado = projetoService.create(dto);
        URI location = URI.create("/api/v1/projetos/" + projetoCriado.id());
        return ResponseEntity.created(location).body(projetoCriado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjetoViewDTO> updateProject(@PathVariable Long id, @Valid @RequestBody ProjetoUpdateDTO dto) {
        ProjetoViewDTO projetoAtualizado = projetoService.update(id, dto);
        return ResponseEntity.ok(projetoAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        projetoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // O MÉTODO DUPLICADO QUE ESTAVA AQUI FOI REMOVIDO. - lucas
}