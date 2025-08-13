package com.example.projetofinal.model.dto;

/**
 * DTO para a visualização de um Avaliador.
 */
public record AvaliadorViewDTO(
        Long id,
        String dadosPessoais,
        String infoEspecificas
) {}