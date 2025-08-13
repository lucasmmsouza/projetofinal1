package com.example.projetofinal.model.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

/**
 * DTO para a criação de um novo projeto.
 * Contém todos os campos necessários para o cadastro inicial, incluindo os IDs
 * das entidades relacionadas (Prêmio e Autores).
 * [cite: 79, 98]
 */
public record ProjetoCreateDTO(
        @NotBlank(message = "O título não pode estar em branco.")
        @Size(max = 255, message = "O título deve ter no máximo 255 caracteres.")
        String titulo,

        @NotBlank(message = "O resumo não pode estar em branco.")
        String resumo,

        @NotBlank(message = "A área temática não pode estar em branco.")
        @Size(max = 100, message = "A área temática deve ter no máximo 100 caracteres.")
        String areaTematica,

        @NotNull(message = "O ID do prêmio é obrigatório.")
        Long premioId,

        @NotEmpty(message = "O projeto deve ter pelo menos um autor.")
        Set<Long> autorIds
) {}