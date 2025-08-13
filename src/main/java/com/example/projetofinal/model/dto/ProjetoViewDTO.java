package com.example.projetofinal.model.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

/**
 * DTO para a visualização de um projeto.
 * Usado para retornar dados de projetos para o frontend, expondo apenas
 * informações seguras e necessárias para a interface, além de dados de
 * autores relacionados.
 * [cite: 80, 98]
 */
public record ProjetoViewDTO(
        Long id,
        String titulo,
        String resumo,
        String areaTematica,
        LocalDateTime dataEnvio,
        String status,
        List<AutorViewDTO> autores // Inclui dados dos autores para cumprir o requisito [cite: 216]
) {}