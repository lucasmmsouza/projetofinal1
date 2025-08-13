package com.example.projetofinal.model.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PremioUpdateDTO(
        @NotBlank(message = "O nome do prêmio não pode estar em branco.")
        String nome,
        @NotBlank(message = "A descrição não pode estar em branco.")
        String descricao,
        @NotNull(message = "O ano da edição é obrigatório.")
        @Min(value = 2000, message = "O ano da edição deve ser válido.")
        int anoEdicao
) {}