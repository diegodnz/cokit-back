package com.pc.dto.Mensagem;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class MensagemInput {

    @NotBlank
    private String mensagem;

    @NotNull
    private Long destinatarioID;

    public MensagemInput(String mensagem, Long destinatarioID) {
        this.mensagem = mensagem;
        this.destinatarioID = destinatarioID;
    }

    public Long getDestinatarioID() {
        return destinatarioID;
    }

    public void setDestinatarioID(Long destinatarioID) {
        this.destinatarioID = destinatarioID;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
}
