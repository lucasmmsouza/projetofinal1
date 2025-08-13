package com.example.projetofinal.model.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * DTO para a criação de um novo Autor.
 */
public record AutorCreateDTO(
        @NotBlank(message = "Os dados pessoais do autor não podem estar em branco.")
        String dadosPessoais
) {}