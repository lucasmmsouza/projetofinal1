package com.example.projetofinal.model.dto;

/**
 * DTO para visualização de um Autor.
 * Usado para expor os dados públicos do autor de forma segura.
 */
public record AutorViewDTO(
        Long id,
        String dadosPessoais
) {}