package com.pc.dto.Produto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pc.model.Usuario;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ProdutoInput {

    @NotBlank
    private String nome;

    @NotBlank
    private String local;

    @NotNull
    private Double preco;

    public ProdutoInput() {}

    public ProdutoInput(String nome, String local, Double preco) {
        this.nome = nome;
        this.local = local;
        this.preco = preco;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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
