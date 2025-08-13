package com.example.projetofinal.model.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

/**
 * DTO para a atualização de um projeto existente.
 * Expõe apenas os campos que podem ser modificados após a criação.
 * [cite: 98]
 */
public record ProjetoUpdateDTO(
        @NotBlank(message = "O título não pode estar em branco.")
        @Size(max = 255, message = "O título deve ter no máximo 255 caracteres.")
        String titulo,

        @NotBlank(message = "O resumo não pode estar em branco.")
        String resumo,

        @NotBlank(message = "A área temática não pode estar em branco.")
        @Size(max = 100, message = "A área temática deve ter no máximo 100 caracteres.")
        String areaTematica,

        @NotBlank(message = "O status não pode estar em branco.")
        String status
) {}