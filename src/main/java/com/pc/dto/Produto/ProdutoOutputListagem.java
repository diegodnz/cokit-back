package com.pc.dto.Produto;

import com.pc.dto.Usuario.UsuarioLocatarioDto;
import com.pc.model.Usuario;

import javax.persistence.ColumnResult;

public class ProdutoOutputListagem {

    private Long id;

    private String nome;

    private UsuarioLocatarioDto locatario;

    private String local;

    private Double preco;

    private Double avaliacao;

    public ProdutoOutputListagem() {}

    public ProdutoOutputListagem(Long id, Double avaliacao, String local, String nome, Double preco, Long usuario_id, String usuario_email, String usuario_nome) {
        this.id = id;
        this.nome = nome;
        this.local = local;
        this.preco = preco;
        this.avaliacao = avaliacao;
        this.locatario = new UsuarioLocatarioDto(usuario_id, usuario_email, usuario_nome);
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

    public UsuarioLocatarioDto getLocatario() {
        return locatario;
    }

    public void setLocatario(UsuarioLocatarioDto locatario) {
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
