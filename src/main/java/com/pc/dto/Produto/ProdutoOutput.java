package com.pc.dto.Produto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pc.model.Usuario;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ProdutoOutput {

    private Long id;

    private String nome;

    private Usuario locatario;

    private String local;

    private Double preco;

    private Double avaliacao;

    public ProdutoOutput() {}

    public ProdutoOutput(Long id, String nome, Usuario locatario, String local, Double preco, Double avaliacao) {
        this.id = id;
        this.nome = nome;
        this.locatario = locatario;
        this.local = local;
        this.preco = preco;
        this.avaliacao = avaliacao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Usuario getLocatario() {
        return locatario;
    }

    public void setLocatario(Usuario locatario) {
        this.locatario = locatario;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public Double getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(Double avaliacao) {
        this.avaliacao = avaliacao;
    }
}
