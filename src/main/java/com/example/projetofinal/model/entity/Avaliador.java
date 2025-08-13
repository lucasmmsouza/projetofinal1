// backend/src/main/java/com/example/projetofinal/model/entity/Avaliador.java
package com.example.projetofinal.model.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Avaliador {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String dadosPessoais;
    private String infoEspecificas;

    @OneToMany(mappedBy = "avaliador", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Avaliacao> avaliacoes;

    // Getters e Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDadosPessoais() {
        return dadosPessoais;
    }

    public void setDadosPessoais(String dadosPessoais) {
        this.dadosPessoais = dadosPessoais;
    }

    public String getInfoEspecificas() {
        return infoEspecificas;
    }

    public void setInfoEspecificas(String infoEspecificas) {
        this.infoEspecificas = infoEspecificas;
    }

    public List<Avaliacao> getAvaliacoes() {
        return avaliacoes;
    }

    public void setAvaliacoes(List<Avaliacao> avaliacoes) {
        this.avaliacoes = avaliacoes;
    }
}