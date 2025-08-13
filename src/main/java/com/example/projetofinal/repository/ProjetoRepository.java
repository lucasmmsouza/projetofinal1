package com.example.projetofinal.repository;

import com.example.projetofinal.model.entity.Projeto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositório para a entidade Projeto.
 * Contém métodos para as operações de CRUD e consultas personalizadas
 * para atender aos requisitos de listagem do sistema.
 */
@Repository
public interface ProjetoRepository extends JpaRepository<Projeto, Long> {

    /**
     * Lista todos os projetos que possuem um determinado status.
     * Usado para encontrar projetos que ainda não foram avaliados[cite: 32].
     * Esta é uma consulta derivada pelo nome do método, conforme sugerido na arquitetura[cite: 146].
     *
     * @param status O status do projeto (ex: "SUBMETIDO").
     * @return Uma lista de projetos que correspondem ao status.
     */
    List<Projeto> findByStatus(String status);

    /**
     * Lista os projetos já avaliados em ordem decrescente da média de suas notas.
     * Utiliza a anotação @Query para definir uma consulta JPQL mais complexa[cite: 147].
     * Esta consulta é usada para determinar a listagem de projetos vencedores[cite: 35].
     *
     * @return Uma lista de projetos ordenados pela nota da avaliação.
     */
    @Query("SELECT p FROM Projeto p JOIN p.avaliacoes a WHERE p.status = 'AVALIADO' GROUP BY p ORDER BY AVG(a.nota) DESC")
    List<Projeto> findProjetosVencedores();
}