package com.example.projetofinal.controller;

import com.example.projetofinal.model.dto.AvaliacaoCreateDTO;
import com.example.projetofinal.model.dto.AvaliacaoViewDTO;
import com.example.projetofinal.service.AvaliacaoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/avaliacoes")
public class AvaliacaoController {

    private final AvaliacaoService avaliacaoService;

    public AvaliacaoController(AvaliacaoService avaliacaoService) {
        this.avaliacaoService = avaliacaoService;
    }

    @PostMapping
    public ResponseEntity<AvaliacaoViewDTO> submitEvaluation(@Valid @RequestBody AvaliacaoCreateDTO dto) {
        AvaliacaoViewDTO avaliacaoCriada = avaliacaoService.create(dto);
        return new ResponseEntity<>(avaliacaoCriada, HttpStatus.CREATED);
    }

    @GetMapping("/projeto/{projetoId}")
    public ResponseEntity<List<AvaliacaoViewDTO>> getEvaluationsByProjectId(@PathVariable Long projetoId) {
        List<AvaliacaoViewDTO> avaliacoes = avaliacaoService.findByProjetoId(projetoId);
        return ResponseEntity.ok(avaliacoes);
    }
}