package com.example.projetofinal.repository;

import com.example.projetofinal.model.entity.Avaliacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Long> {

    Optional<Avaliacao> findByProjetoIdAndAvaliadorId(Long projetoId, Long avaliadorId);

    // Método que estava faltando
    List<Avaliacao> findByProjetoId(Long projetoId);
}