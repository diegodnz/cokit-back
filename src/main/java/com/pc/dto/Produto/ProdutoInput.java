package com.pc.dto.Produto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pc.model.Usuario;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class ProdutoInput {

    @NotBlank
    private String nome;

    private String descricao;

    @NotBlank
    private String local;

    @NotNull
    private Double preco;

    private String imagem;

    private LocalDate dataInicial;

    private LocalDate dataFinal;

    public ProdutoInput() {}

    public ProdutoInput(String nome, String descricao, String local, Double preco, String imagem, LocalDate dataInicial, LocalDate dataFinal) {
        this.nome = nome;
        this.descricao = descricao;
        this.local = local;
        this.preco = preco;
        this.imagem = imagem;
        this.dataInicial = dataInicial;
        this.dataFinal = dataFinal;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
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
