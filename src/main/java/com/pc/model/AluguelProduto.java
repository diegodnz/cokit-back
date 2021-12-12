package com.pc.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
public class AluguelProduto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Usuario locador;

    @ManyToOne
    private Produto produto;

    private LocalDate dataInicial;

    private LocalDate dataFinal;

    public AluguelProduto() {}

    public AluguelProduto(Usuario locador, LocalDate dataInicial, LocalDate dataFinal, Produto produto) {
        this.id = id;
        this.locador = locador;
        this.dataInicial = dataInicial;
        this.dataFinal = dataFinal;
        this.produto = produto;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getLocador() {
        return locador;
    }

    public void setLocador(Usuario locador) {
        this.locador = locador;
    }

    public LocalDate getDataInicial() {
        return dataInicial;
    }

    public void setDataInicial(LocalDate dataInicial) {
        this.dataInicial = dataInicial;
    }

    public LocalDate getDataFinal() {
        return dataFinal;
    }

    public void setDataFinal(LocalDate dataFinal) {
        this.dataFinal = dataFinal;
    }
}
