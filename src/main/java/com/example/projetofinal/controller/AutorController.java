package com.example.projetofinal.controller;

import com.example.projetofinal.model.dto.AutorCreateDTO;
import com.example.projetofinal.model.dto.AutorComProjetosDTO;
import com.example.projetofinal.service.AutorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/autores")
public class AutorController {

    private final AutorService autorService;

    public AutorController(AutorService autorService) {
        this.autorService = autorService;
    }

    @PostMapping
    public ResponseEntity<AutorComProjetosDTO> createAutor(@Valid @RequestBody AutorCreateDTO dto) {
        return new ResponseEntity<>(autorService.create(dto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<AutorComProjetosDTO>> getAllAutores() {
        return ResponseEntity.ok(autorService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AutorComProjetosDTO> getAutorById(@PathVariable Long id) {
        return ResponseEntity.ok(autorService.findById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAutor(@PathVariable Long id) {
        autorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}