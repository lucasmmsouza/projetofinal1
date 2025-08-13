package com.example.projetofinal.service;

import com.example.projetofinal.model.dto.*;
import com.example.projetofinal.model.entity.Autor;
import com.example.projetofinal.model.entity.Premio;
import com.example.projetofinal.model.entity.Projeto;
import com.example.projetofinal.repository.AutorRepository;
import com.example.projetofinal.repository.PremioRepository;
import com.example.projetofinal.repository.ProjetoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProjetoService {

    private final ProjetoRepository projetoRepository;
    private final AutorRepository autorRepository;
    private final PremioRepository premioRepository;

    public ProjetoService(ProjetoRepository projetoRepository, AutorRepository autorRepository, PremioRepository premioRepository) {
        this.projetoRepository = projetoRepository;
        this.autorRepository = autorRepository;
        this.premioRepository = premioRepository;
    }

    @Transactional(readOnly = true)
    public List<ProjetoViewDTO> findAll() {
        return projetoRepository.findAll().stream()
                .map(this::toProjetoViewDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ProjetoViewDTO findById(Long id) {
        Projeto projeto = projetoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Projeto não encontrado com o id: " + id));
        return toProjetoViewDTO(projeto);
    }

    /**
     * [cite_start]Lista os projetos que ainda não foram avaliados[cite: 218].
     */
    @Transactional(readOnly = true)
    public List<ProjetoViewDTO> findUnevaluated() {
        return projetoRepository.findByStatus("SUBMETIDO").stream()
                .map(this::toProjetoViewDTO)
                .collect(Collectors.toList());
    }

    /**
     * [cite_start]Lista os projetos vencedores, calculando a nota final média de cada um[cite: 221].
     */
    @Transactional(readOnly = true)
    public List<ProjetoVencedorDTO> findWinningProjects() {
        List<Projeto> projetos = projetoRepository.findProjetosVencedores();
        return projetos.stream().map(projeto -> {
            // Calcula a média das notas das avaliações
            BigDecimal notaMedia = projeto.getAvaliacoes().stream()
                    .map(a -> a.getNota())
                    .reduce(BigDecimal.ZERO, BigDecimal::add)
                    .divide(BigDecimal.valueOf(projeto.getAvaliacoes().size()), 2, RoundingMode.HALF_UP);

            // Extrai os nomes dos autores
            List<String> nomesAutores = projeto.getAutores().stream()
                    .map(Autor::getDadosPessoais)
                    .collect(Collectors.toList());

            return new ProjetoVencedorDTO(projeto.getId(), projeto.getTitulo(), nomesAutores, notaMedia);
        }).collect(Collectors.toList());
    }


    @Transactional
    public ProjetoViewDTO create(ProjetoCreateDTO dto) {
        // Busca as entidades relacionadas
        Premio premio = premioRepository.findById(dto.premioId())
                .orElseThrow(() -> new EntityNotFoundException("Prêmio não encontrado com o id: " + dto.premioId()));
        Set<Autor> autores = new HashSet<>(autorRepository.findAllById(dto.autorIds()));
        if (autores.size() != dto.autorIds().size()) {
            throw new EntityNotFoundException("Um ou mais autores não foram encontrados.");
        }

        // Cria e salva a nova entidade Projeto
        Projeto novoProjeto = new Projeto();
        novoProjeto.setTitulo(dto.titulo());
        novoProjeto.setResumo(dto.resumo());
        novoProjeto.setAreaTematica(dto.areaTematica());
        novoProjeto.setPremio(premio);
        novoProjeto.setAutores(autores);
        novoProjeto.setDataEnvio(LocalDateTime.now());
        novoProjeto.setStatus("SUBMETIDO"); // Status inicial

        Projeto projetoSalvo = projetoRepository.save(novoProjeto);
        return toProjetoViewDTO(projetoSalvo);
    }

    @Transactional
    public ProjetoViewDTO update(Long id, ProjetoUpdateDTO dto) {
        Projeto projetoExistente = projetoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Projeto não encontrado para atualização com o id: " + id));

        projetoExistente.setTitulo(dto.titulo());
        projetoExistente.setResumo(dto.resumo());
        projetoExistente.setAreaTematica(dto.areaTematica());
        projetoExistente.setStatus(dto.status());

        Projeto projetoAtualizado = projetoRepository.save(projetoExistente);
        return toProjetoViewDTO(projetoAtualizado);
    }

    @Transactional
    public void delete(Long id) {
        if (!projetoRepository.existsById(id)) {
            throw new EntityNotFoundException("Projeto não encontrado para exclusão com o id: " + id);
        }
        projetoRepository.deleteById(id);
    }

    // Método auxiliar para converter a entidade Projeto em seu DTO de visualização.
    private ProjetoViewDTO toProjetoViewDTO(Projeto projeto) {
        List<AutorViewDTO> autoresDTO = projeto.getAutores().stream()
                .map(autor -> new AutorViewDTO(autor.getId(), autor.getDadosPessoais()))
                .collect(Collectors.toList());

        return new ProjetoViewDTO(
                projeto.getId(),
                projeto.getTitulo(),
                projeto.getResumo(),
                projeto.getAreaTematica(),
                projeto.getDataEnvio(),
                projeto.getStatus(),
                autoresDTO
        );
    }
}