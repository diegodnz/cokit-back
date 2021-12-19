package com.pc.dto.Produto;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.pc.dto.AluguelProduto.IntervaloDatas;
import com.pc.dto.AluguelProduto.UsuarioAluguelDatas;
import com.pc.dto.Usuario.UsuarioLocatarioDto;
import com.pc.model.Usuario;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProdutoOutputAlugadosLocatarioListagem {

    private Long id;

    private String nome;

    private UsuarioLocatarioDto locatario;

    private String local;

    private Double preco;

    private Double avaliacao;

    private String imagem;

    private Map<UsuarioLocatarioDto, List<IntervaloDatas>> alugueisUsuarios;

    public ProdutoOutputAlugadosLocatarioListagem() {}

    public ProdutoOutputAlugadosLocatarioListagem(Long id, Double avaliacao, String local, String nome, Double preco, Long usuario_id, String usuario_email, String usuario_nome, Map<UsuarioLocatarioDto, List<IntervaloDatas>> alugueisUsuarios, String imagem) {
        this.id = id;
        this.nome = nome;
        this.local = local;
        this.preco = preco;
        this.avaliacao = avaliacao;
        this.locatario = new UsuarioLocatarioDto(usuario_id, usuario_email, usuario_nome);
        this.alugueisUsuarios = alugueisUsuarios;
        this.imagem = imagem;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
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

    public List<UsuarioAluguelDatas> getAlugueisUsuarios() {
        List<UsuarioAluguelDatas> usuarios = new ArrayList<>();
        for (UsuarioLocatarioDto usuario : alugueisUsuarios.keySet()) {
            usuarios.add(new UsuarioAluguelDatas(usuario, alugueisUsuarios.get(usuario)));
        }
        return usuarios;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public Map<UsuarioLocatarioDto, List<IntervaloDatas>> getMapAlugueisUsuarios() {
        return alugueisUsuarios;
    }

    public void setAlugueisUsuarios(Map<UsuarioLocatarioDto, List<IntervaloDatas>> alugueisUsuarios) {
        this.alugueisUsuarios = alugueisUsuarios;
    }
}