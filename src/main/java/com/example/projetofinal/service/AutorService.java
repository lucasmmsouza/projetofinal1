package com.example.projetofinal.service;

import com.example.projetofinal.model.dto.AutorComProjetosDTO;
import com.example.projetofinal.model.dto.AutorCreateDTO;
import com.example.projetofinal.model.dto.ProjetoResumoDTO;
import com.example.projetofinal.model.entity.Autor;
import com.example.projetofinal.model.entity.Projeto; // Importe a entidade Projeto
import com.example.projetofinal.repository.AutorRepository;
import com.example.projetofinal.repository.ProjetoRepository; // 1. Importe o ProjetoRepository
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.HashSet; // Importe o HashSet
import java.util.Set;    // Importe o Set
import java.util.stream.Collectors;

@Service
public class AutorService {

    private final AutorRepository autorRepository;
    private final ProjetoRepository projetoRepository; // 2. Adicione o repositório como um campo

    // 3. Modifique o construtor para injetar o novo repositório
    public AutorService(AutorRepository autorRepository, ProjetoRepository projetoRepository) {
        this.autorRepository = autorRepository;
        this.projetoRepository = projetoRepository;
    }

    @Transactional
    public AutorComProjetosDTO create(AutorCreateDTO dto) {
        Autor novoAutor = new Autor();
        novoAutor.setDadosPessoais(dto.dadosPessoais());
        Autor autorSalvo = autorRepository.save(novoAutor);
        return toAutorComProjetosDTO(autorSalvo);
    }

    @Transactional(readOnly = true)
    public List<AutorComProjetosDTO> findAll() {
        return autorRepository.findAll().stream()
                .map(this::toAutorComProjetosDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public AutorComProjetosDTO findById(Long id) {
        Autor autor = autorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Autor não encontrado com o id: " + id));
        return toAutorComProjetosDTO(autor);
    }

    /**
     * Apaga um autor e todos os projetos associados a ele.
     * ATENÇÃO: Esta operação é destrutiva e removerá projetos mesmo que
     * eles tenham outros coautores.
     *
     * @param id O ID do autor a ser apagado.
     */
    @Transactional
    public void delete(Long id) {
        // 4. Implemente a nova lógica de deleção
        Autor autor = autorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Autor não encontrado para exclusão com o id: " + id));

        // Pega uma cópia da lista de projetos do autor
        Set<Projeto> projetosParaDeletar = new HashSet<>(autor.getProjetos());

        if (!projetosParaDeletar.isEmpty()) {
            // Para cada projeto, remove a associação com todos os seus autores
            // para evitar problemas de concorrência na tabela de junção.
            for (Projeto projeto : projetosParaDeletar) {
                projeto.getAutores().clear();
            }
            // Deleta todos os projetos de uma vez
            projetoRepository.deleteAll(projetosParaDeletar);
        }

        // Finalmente, apaga o autor, que agora não tem mais associações
        autorRepository.delete(autor);
    }

    private AutorComProjetosDTO toAutorComProjetosDTO(Autor autor) {
        List<ProjetoResumoDTO> projetosDTO = (autor.getProjetos() != null) ?
                autor.getProjetos().stream()
                        .map(projeto -> new ProjetoResumoDTO(projeto.getId(), projeto.getTitulo()))
                        .collect(Collectors.toList()) : Collections.emptyList();

        return new AutorComProjetosDTO(
                autor.getId(),
                autor.getDadosPessoais(),
                projetosDTO
        );
    }
}