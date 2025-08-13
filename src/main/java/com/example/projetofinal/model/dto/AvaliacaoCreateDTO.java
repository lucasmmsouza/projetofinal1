package com.example.projetofinal.model.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

/**
 * DTO para a submissão (criação) de uma nova avaliação.
 * Contém os campos necessários que um avaliador envia para registrar seu parecer,
 * com validações para garantir a integridade dos dados de entrada.
 */
public record AvaliacaoCreateDTO(
        @NotNull(message = "O ID do projeto é obrigatório.")
        Long projetoId,

        @NotNull(message = "O ID do avaliador é obrigatório.")
        Long avaliadorId,

        @NotBlank(message = "O parecer descritivo não pode estar em branco.")
        String parecer,

        @NotNull(message = "A nota é obrigatória.")
        @DecimalMin(value = "0.0", inclusive = true, message = "A nota não pode ser menor que 0.0.")
        @DecimalMax(value = "10.0", inclusive = true, message = "A nota não pode ser maior que 10.0.")
        BigDecimal nota
) {}