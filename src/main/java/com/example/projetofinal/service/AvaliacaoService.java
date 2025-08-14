package com.example.projetofinal.service;

import com.example.projetofinal.model.dto.AvaliacaoCreateDTO;
import com.example.projetofinal.model.dto.AvaliacaoViewDTO;
import com.example.projetofinal.model.entity.Avaliacao;
import com.example.projetofinal.model.entity.Avaliador;
import com.example.projetofinal.model.entity.Projeto;
import com.example.projetofinal.repository.AvaliacaoRepository;
import com.example.projetofinal.repository.AvaliadorRepository;
import com.example.projetofinal.repository.ProjetoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AvaliacaoService {

    private final AvaliacaoRepository avaliacaoRepository;
    private final ProjetoRepository projetoRepository;
    private final AvaliadorRepository avaliadorRepository;

    public AvaliacaoService(AvaliacaoRepository avaliacaoRepository, ProjetoRepository projetoRepository, AvaliadorRepository avaliadorRepository) {
        this.avaliacaoRepository = avaliacaoRepository;
        this.projetoRepository = projetoRepository;
        this.avaliadorRepository = avaliadorRepository;
    }

    /**
     * Submete uma nova avaliação para um projeto.
     * Aplica regras de negócio para garantir que um avaliador não possa avaliar o próprio projeto
     * e que um projeto não seja avaliado duas vezes pelo mesmo avaliador.
     *
     * @param dto O DTO com os dados da nova avaliação.
     * @return um DTO com os dados da avaliação criada.
     */
    @Transactional
    public AvaliacaoViewDTO create(AvaliacaoCreateDTO dto) {
        // 1. Busca as entidades relacionadas
        Projeto projeto = projetoRepository.findById(dto.projetoId())
                .orElseThrow(() -> new EntityNotFoundException("Projeto não encontrado com o id: " + dto.projetoId()));
        Avaliador avaliador = avaliadorRepository.findById(dto.avaliadorId())
                .orElseThrow(() -> new EntityNotFoundException("Avaliador não encontrado com o id: " + dto.avaliadorId()));

        // 2. Aplica as regras de negócio
        // Regra: Um avaliador não pode avaliar um projeto do qual seja autor.
        boolean isAutor = projeto.getAutores().stream().anyMatch(autor -> autor.getDadosPessoais().equals(avaliador.getDadosPessoais()));
        if (isAutor) {
            throw new IllegalArgumentException("Conflito: O avaliador é um dos autores do projeto.");
        }

        // Regra: Um projeto só pode ser avaliado uma vez por cada avaliador.
        avaliacaoRepository.findByProjetoIdAndAvaliadorId(dto.projetoId(), dto.avaliadorId())
                .ifPresent(a -> {
                    throw new IllegalArgumentException("Conflito: Este avaliador já avaliou este projeto.");
                });

        // 3. Cria e salva a nova avaliação
        Avaliacao novaAvaliacao = new Avaliacao();
        novaAvaliacao.setProjeto(projeto);
        novaAvaliacao.setAvaliador(avaliador);
        novaAvaliacao.setParecer(dto.parecer());
        novaAvaliacao.setNota(dto.nota());
        novaAvaliacao.setDataAvaliacao(LocalDateTime.now());

        Avaliacao avaliacaoSalva = avaliacaoRepository.save(novaAvaliacao);

        // 4. Atualiza o status do projeto para "AVALIADO"
        projeto.setStatus("AVALIADO");
        projetoRepository.save(projeto);

        // 5. Retorna o DTO de visualização
        return toAvaliacaoViewDTO(avaliacaoSalva);
    }

    /**
     * Busca todas as avaliações de um projeto específico.
     *
     * @param projetoId O ID do projeto.
     * @return Uma lista de DTOs de visualização das avaliações.
     */
    @Transactional(readOnly = true)
    public List<AvaliacaoViewDTO> findByProjetoId(Long projetoId) {
        if (!projetoRepository.existsById(projetoId)) {
            throw new EntityNotFoundException("Projeto não encontrado com o id: " + projetoId);
        }

        List<Avaliacao> avaliacoes = avaliacaoRepository.findByProjetoId(projetoId);
        return avaliacoes.stream()
                .map(this::toAvaliacaoViewDTO)
                .collect(Collectors.toList());
    }

    // Método auxiliar para converter Entidade para DTO
    private AvaliacaoViewDTO toAvaliacaoViewDTO(Avaliacao avaliacao) {
        return new AvaliacaoViewDTO(
                avaliacao.getId(),
                avaliacao.getParecer(),
                avaliacao.getNota(),
                avaliacao.getDataAvaliacao(),
                avaliacao.getAvaliador().getId(),
                avaliacao.getAvaliador().getDadosPessoais() // Exemplo de dado agregado útil
        );
    }
}