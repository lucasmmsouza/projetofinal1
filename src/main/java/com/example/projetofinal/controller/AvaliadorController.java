package com.example.projetofinal.controller;

import com.example.projetofinal.model.dto.AvaliadorCreateDTO;
import com.example.projetofinal.model.dto.AvaliadorViewDTO;
import com.example.projetofinal.service.AvaliadorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/avaliadores")
public class AvaliadorController {

    private final AvaliadorService avaliadorService;

    public AvaliadorController(AvaliadorService avaliadorService) {
        this.avaliadorService = avaliadorService;
    }

    @PostMapping
    public ResponseEntity<AvaliadorViewDTO> createAvaliador(@Valid @RequestBody AvaliadorCreateDTO dto) {
        return new ResponseEntity<>(avaliadorService.create(dto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<AvaliadorViewDTO>> getAllAvaliadores() {
        return ResponseEntity.ok(avaliadorService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AvaliadorViewDTO> getAvaliadorById(@PathVariable Long id){
        return ResponseEntity.ok(avaliadorService.findById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAvaliador(@PathVariable Long id) {
        avaliadorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}