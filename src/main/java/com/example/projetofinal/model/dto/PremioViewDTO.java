package com.example.projetofinal.model.dto;

/**
 * DTO para a visualização de um Prêmio.
 */
public record PremioViewDTO(
        Long id,
        String nome,
        String descricao,
        int anoEdicao
) {}