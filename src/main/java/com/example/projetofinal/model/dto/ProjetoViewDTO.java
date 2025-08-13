package com.example.projetofinal.model.dto;

import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO para a visualização de um projeto.
 * Usado para retornar dados de projetos para o frontend, expondo apenas
 * informações seguras e necessárias para a interface, além de dados de
 * autores relacionados.
 */
public record ProjetoViewDTO(
        Long id,
        String titulo,
        String resumo,
        String areaTematica,
        LocalDateTime dataEnvio,
        String status,
        List<AutorViewDTO> autores
) {}