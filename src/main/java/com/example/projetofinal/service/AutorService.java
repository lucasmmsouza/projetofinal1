package com.example.projetofinal.service;

import com.example.projetofinal.model.dto.AutorComProjetosDTO; // Alterado
import com.example.projetofinal.model.dto.AutorCreateDTO;
import com.example.projetofinal.model.dto.ProjetoResumoDTO;
import com.example.projetofinal.model.entity.Autor;
import com.example.projetofinal.repository.AutorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AutorService {

    private final AutorRepository autorRepository;

    public AutorService(AutorRepository autorRepository) {
        this.autorRepository = autorRepository;
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

    @Transactional
    public void delete(Long id) {
        if (!autorRepository.existsById(id)) {
            throw new EntityNotFoundException("Autor não encontrado para exclusão com o id: " + id);
        }
        autorRepository.deleteById(id);
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