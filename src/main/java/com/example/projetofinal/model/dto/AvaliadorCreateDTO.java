package com.example.projetofinal.model.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * DTO para a criação de um novo Avaliador.
 */
public record AvaliadorCreateDTO(
        @NotBlank(message = "Os dados pessoais do avaliador não podem estar em branco.")
        String dadosPessoais,
        String infoEspecificas
) {}