package com.pc.dto.Mensagem;

import java.time.LocalDateTime;

public class MensagemOutput {

    private String mensagem;

    private LocalDateTime dataEnvio;

    private Boolean enviou;

    public MensagemOutput(String mensagem, LocalDateTime dataEnvio, Boolean enviou) {
        this.mensagem = mensagem;
        this.dataEnvio = dataEnvio;
        this.enviou = enviou;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public LocalDateTime getDataEnvio() {
        return dataEnvio;
    }

    public void setDataEnvio(LocalDateTime dataEnvio) {
        this.dataEnvio = dataEnvio;
    }

    public Boolean getEnviou() {
        return enviou;
    }

    public void setEnviou(Boolean enviou) {
        this.enviou = enviou;
    }
}
