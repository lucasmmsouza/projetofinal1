package com.example.projetofinal.repository;

import com.example.projetofinal.model.entity.Avaliador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositório para a entidade Avaliador.
 * Provê a camada de acesso a dados para as informações dos avaliadores.
 */
@Repository
public interface AvaliadorRepository extends JpaRepository<Avaliador, Long> {
    // Métodos CRUD são herdados do JpaRepository.
}