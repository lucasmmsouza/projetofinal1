package com.example.projetofinal.model.dto;

import java.math.BigDecimal;
import java.util.List;

/**
 * DTO para exibir os projetos vencedores.
 * Contém informações agregadas como a lista de nomes dos autores e a nota final média.
 *
 */
public record ProjetoVencedorDTO(
        Long id,
        String titulo,
        List<String> nomesAutores,
        BigDecimal notaFinal
) {}