package com.example.projetofinal.service;

import com.example.projetofinal.model.dto.PremioCreateDTO;
import com.example.projetofinal.model.dto.PremioUpdateDTO;
import com.example.projetofinal.model.dto.PremioViewDTO;
import com.example.projetofinal.model.entity.Premio;
import com.example.projetofinal.repository.PremioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PremioService {

    private final PremioRepository premioRepository;

    public PremioService(PremioRepository premioRepository) {
        this.premioRepository = premioRepository;
    }

    @Transactional
    public PremioViewDTO create(PremioCreateDTO dto) {
        Premio novoPremio = new Premio();
        novoPremio.setNome(dto.nome());
        novoPremio.setDescricao(dto.descricao());
        novoPremio.setAnoEdicao(dto.anoEdicao());
        Premio premioSalvo = premioRepository.save(novoPremio);
        return toPremioViewDTO(premioSalvo);
    }

    @Transactional(readOnly = true)
    public List<PremioViewDTO> findAll() {
        return premioRepository.findAll().stream()
                .map(this::toPremioViewDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PremioViewDTO findById(Long id) {
        return premioRepository.findById(id)
                .map(this::toPremioViewDTO)
                .orElseThrow(() -> new EntityNotFoundException("Prêmio não encontrado com o id: " + id));
    }

    @Transactional
    public PremioViewDTO update(Long id, PremioUpdateDTO dto) {
        Premio premioExistente = premioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Prêmio não encontrado para atualização com o id: " + id));
        premioExistente.setNome(dto.nome());
        premioExistente.setDescricao(dto.descricao());
        premioExistente.setAnoEdicao(dto.anoEdicao());
        Premio premioAtualizado = premioRepository.save(premioExistente);
        return toPremioViewDTO(premioAtualizado);
    }

    @Transactional
    public void delete(Long id) {
        if (!premioRepository.existsById(id)) {
            throw new EntityNotFoundException("Prêmio não encontrado para exclusão com o id: " + id);
        }
        premioRepository.deleteById(id);
    }

    private PremioViewDTO toPremioViewDTO(Premio premio) {
        return new PremioViewDTO(
                premio.getId(),
                premio.getNome(),
                premio.getDescricao(),
                premio.getAnoEdicao()
        );
    }
}