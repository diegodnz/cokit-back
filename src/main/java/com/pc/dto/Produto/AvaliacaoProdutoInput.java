package com.pc.dto.Produto;

import javax.validation.constraints.NotNull;

public class AvaliacaoProdutoInput {

    @NotNull
    private Double avaliacao;

    public AvaliacaoProdutoInput() {}

    public AvaliacaoProdutoInput(Double avaliacao) {
        this.avaliacao = avaliacao;
    }

    public Double getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(Double avaliacao) {
        this.avaliacao = avaliacao;
    }
}
