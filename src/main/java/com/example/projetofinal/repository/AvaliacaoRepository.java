package com.example.projetofinal.repository;

import com.example.projetofinal.model.entity.Avaliacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositório para a entidade Avaliacao.
 * Gerencia a persistência dos dados das avaliações dos projetos.
 */
@Repository
public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Long> {

    /**
     * Busca uma avaliação com base no ID do projeto e no ID do avaliador.
     * Este método é útil para verificar se um avaliador já avaliou um projeto específico,
     * garantindo a regra de negócio.
     *
     * @param projetoId O ID do projeto.
     * @param avaliadorId O ID do avaliador.
     * @return um Optional contendo a Avaliacao se encontrada, ou vazio caso contrário.
     */
    Optional<Avaliacao> findByProjetoIdAndAvaliadorId(Long projetoId, Long avaliadorId);
}