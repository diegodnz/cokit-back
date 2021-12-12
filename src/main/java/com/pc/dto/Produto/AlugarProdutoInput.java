package com.pc.dto.Produto;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class AlugarProdutoInput {

    @NotNull
    private Boolean unicoDia;

    @NotNull
    private LocalDate dataInicial;

    private LocalDate dataFinal;

    public AlugarProdutoInput() {}

    public AlugarProdutoInput(Boolean unicoDia, LocalDate dataInicial, LocalDate dataFinal) {
        this.unicoDia = unicoDia;
        this.dataInicial = dataInicial;
        this.dataFinal = dataFinal;
    }

    public Boolean getUnicoDia() {
        return unicoDia;
    }

    public void setUnicoDia(Boolean unicoDia) {
        this.unicoDia = unicoDia;
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
