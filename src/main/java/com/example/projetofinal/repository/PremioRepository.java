package com.example.projetofinal.repository;

import com.example.projetofinal.model.entity.Premio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositório para a entidade Premio.
 * Fornece todas as operações de banco de dados CRUD (Criar, Ler, Atualizar, Apagar)
 * por meio da extensão da interface JpaRepository.
 */
@Repository
public interface PremioRepository extends JpaRepository<Premio, Long> {
}