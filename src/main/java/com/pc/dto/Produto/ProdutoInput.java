package com.pc.dto.Produto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pc.model.Usuario;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ProdutoInput {

    @NotBlank
    private String nome;

    private String descricao;

    @NotBlank
    private String local;

    @NotNull
    private Double preco;

    public ProdutoInput() {}

    public ProdutoInput(String nome, String descricao, String local, Double preco) {
        this.nome = nome;
        this.descricao = descricao;
        this.local = local;
        this.preco = preco;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
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
}
