package com.pc.dto.Produto;

import com.pc.dto.Usuario.UsuarioLocatarioDto;

import java.time.LocalDate;
import java.util.Set;

public class ProdutoOutput {

    private Long id;

    private String nome;

    private String descricao;

    private UsuarioLocatarioDto locatario;

    private String local;

    private Double preco;

    private Double avaliacao;

    private String imagem;

    private LocalDate dataInicial;

    private LocalDate dataFinal;

    private Set<LocalDate> datasAlugadas;

    public ProdutoOutput() {}

    public ProdutoOutput(Long id, String nome, String descricao, Long usuario_id, String usuario_email, String usuario_nome, String local, Double preco, Double avaliacao, Set<LocalDate> datasAlugadas, String imagem, LocalDate dataInicial, LocalDate dataFinal) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.locatario = new UsuarioLocatarioDto(usuario_id, usuario_email, usuario_nome);
        this.local = local;
        this.preco = preco;
        this.avaliacao = avaliacao;
        this.datasAlugadas = datasAlugadas;
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

    public Set<LocalDate> getDatasAlugadas() {
        return datasAlugadas;
    }

    public void setDatasAlugadas(Set<LocalDate> datasAlugadas) {
        this.datasAlugadas = datasAlugadas;
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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
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
