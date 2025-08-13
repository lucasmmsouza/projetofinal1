package com.example.projetofinal.model.dto;

import java.util.List;

/**
 * DTO para visualização de um Autor com a lista de seus projetos.
 */
public record AutorComProjetosDTO(
        Long id,
        String dadosPessoais,
        List<ProjetoResumoDTO> projetos
) {
}