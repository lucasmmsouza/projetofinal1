package com.example.projetofinal.service;

import com.example.projetofinal.model.dto.AvaliadorCreateDTO;
import com.example.projetofinal.model.dto.AvaliadorViewDTO;
import com.example.projetofinal.model.entity.Avaliador;
import com.example.projetofinal.repository.AvaliadorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AvaliadorService {

    private final AvaliadorRepository avaliadorRepository;

    public AvaliadorService(AvaliadorRepository avaliadorRepository) {
        this.avaliadorRepository = avaliadorRepository;
    }

    @Transactional
    public AvaliadorViewDTO create(AvaliadorCreateDTO dto) {
        Avaliador novoAvaliador = new Avaliador();
        novoAvaliador.setDadosPessoais(dto.dadosPessoais());
        novoAvaliador.setInfoEspecificas(dto.infoEspecificas());
        Avaliador avaliadorSalvo = avaliadorRepository.save(novoAvaliador);
        return toAvaliadorViewDTO(avaliadorSalvo);
    }

    @Transactional(readOnly = true)
    public List<AvaliadorViewDTO> findAll() {
        return avaliadorRepository.findAll().stream()
                .map(this::toAvaliadorViewDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public AvaliadorViewDTO findById(Long id) {
        return avaliadorRepository.findById(id)
                .map(this::toAvaliadorViewDTO)
                .orElseThrow(() -> new EntityNotFoundException("Avaliador não encontrado com o id: " + id));
    }

    @Transactional
    public void delete(Long id) {
        if (!avaliadorRepository.existsById(id)) {
            throw new EntityNotFoundException("Avaliador não encontrado para exclusão com o id: " + id);
        }
        avaliadorRepository.deleteById(id);
    }

    private AvaliadorViewDTO toAvaliadorViewDTO(Avaliador avaliador) {
        return new AvaliadorViewDTO(
                avaliador.getId(),
                avaliador.getDadosPessoais(),
                avaliador.getInfoEspecificas()
        );
    }
}