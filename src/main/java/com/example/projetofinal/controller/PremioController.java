package com.example.projetofinal.controller;

import com.example.projetofinal.model.dto.PremioCreateDTO;
import com.example.projetofinal.model.dto.PremioUpdateDTO;
import com.example.projetofinal.model.dto.PremioViewDTO;
import com.example.projetofinal.service.PremioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/premios")
public class PremioController {

    private final PremioService premioService;

    public PremioController(PremioService premioService) {
        this.premioService = premioService;
    }

    @PostMapping
    public ResponseEntity<PremioViewDTO> createPremio(@Valid @RequestBody PremioCreateDTO dto) {
        return new ResponseEntity<>(premioService.create(dto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PremioViewDTO>> getAllPremios() {
        return ResponseEntity.ok(premioService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PremioViewDTO> getPremioById(@PathVariable Long id) {
        return ResponseEntity.ok(premioService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PremioViewDTO> updatePremio(@PathVariable Long id, @Valid @RequestBody PremioUpdateDTO dto) {
        return ResponseEntity.ok(premioService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePremio(@PathVariable Long id) {
        premioService.delete(id);
        return ResponseEntity.noContent().build();
    }
}