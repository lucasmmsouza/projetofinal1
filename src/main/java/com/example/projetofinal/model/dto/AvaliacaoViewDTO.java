package com.example.projetofinal.model.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO para a visualização de uma avaliação existente.
 * Usado para retornar dados de uma avaliação para o frontend de forma segura,
 * sem expor a entidade JPA completa e agregando informações úteis como o nome do avaliador.
 */
public record AvaliacaoViewDTO(
        Long id,
        String parecer,
        BigDecimal nota,
        LocalDateTime dataAvaliacao,
        Long avaliadorId,
        String nomeAvaliador // Campo adicional para facilitar a exibição na interface
) {}