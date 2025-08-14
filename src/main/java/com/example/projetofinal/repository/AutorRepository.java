package com.example.projetofinal.repository;

import com.example.projetofinal.model.entity.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositório para a entidade Autor.
 * Responsável pelas operações de persistência de dados para os autores.
 */
@Repository
public interface AutorRepository extends JpaRepository<Autor, Long> {
}