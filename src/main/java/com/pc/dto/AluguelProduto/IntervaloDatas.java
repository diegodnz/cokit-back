package com.pc.dto.AluguelProduto;

import java.time.LocalDate;

public class IntervaloDatas {

    private LocalDate data_inicial;

    private LocalDate data_final;

    public IntervaloDatas() {}

    public IntervaloDatas(LocalDate data_inicial, LocalDate data_final) {
        this.data_inicial = data_inicial;
        this.data_final = data_final;
    }

    public LocalDate getDataInicial() {
        return data_inicial;
    }

    public void setDataInicial(LocalDate data_inicial) {
        this.data_inicial = data_inicial;
    }

    public LocalDate getDataFinal() {
        return data_final;
    }

    public void setDataFinal(LocalDate data_final) {
        this.data_final = data_final;
    }
}
