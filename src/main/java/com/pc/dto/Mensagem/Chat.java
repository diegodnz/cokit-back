package com.pc.dto.Mensagem;

import com.pc.model.Mensagens;

import java.util.List;

public class Chat {

    private Long usuarioId;

    private String usuarioEmail;

    private String usuarioNome;

    private List<MensagemOutput> mensagens;

    public Chat(Long usuarioId, String usuarioEmail, String usuarioNome, List<MensagemOutput> mensagens) {
        this.usuarioId = usuarioId;
        this.usuarioEmail = usuarioEmail;
        this.usuarioNome = usuarioNome;
        this.mensagens = mensagens;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getUsuarioEmail() {
        return usuarioEmail;
    }

    public void setUsuarioEmail(String usuarioEmail) {
        this.usuarioEmail = usuarioEmail;
    }

    public String getUsuarioNome() {
        return usuarioNome;
    }

    public void setUsuarioNome(String usuarioNome) {
        this.usuarioNome = usuarioNome;
    }

    public List<MensagemOutput> getMensagens() {
        return mensagens;
    }

    public void setMensagens(List<MensagemOutput> mensagens) {
        this.mensagens = mensagens;
    }

    public void addMensagem(MensagemOutput mensagem) {
        this.mensagens.add(mensagem);
    }
}
