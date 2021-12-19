package com.pc.model;


import javax.persistence.*;
import java.io.Serializable;

@Entity
public class AvaliacaoProduto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Usuario avaliador;

    @ManyToOne
    private Produto produto;

    private Double avaliacao;

    public AvaliacaoProduto() {}

    public AvaliacaoProduto(Usuario avaliador, Produto produto, Double avaliacao) {
        this.avaliador = avaliador;
        this.produto = produto;
        this.avaliacao = avaliacao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getAvaliador() {
        return avaliador;
    }

    public void setAvaliador(Usuario avaliador) {
        this.avaliador = avaliador;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Double getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(Double avaliacao) {
        this.avaliacao = avaliacao;
    }
}