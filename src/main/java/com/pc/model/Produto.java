package com.pc.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pc.dto.Produto.ProdutoOutputListagem;
import org.apache.tomcat.jni.Local;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@SqlResultSetMapping(
        name = "ProdutoOutListagem",
        classes = @ConstructorResult(
                targetClass = ProdutoOutputListagem.class,
                columns = {
                        @ColumnResult(name = "id", type = Long.class),
                        @ColumnResult(name = "avaliacao", type = Double.class),
                        @ColumnResult(name = "local"),
                        @ColumnResult(name = "nome"),
                        @ColumnResult(name = "preco", type = Double.class),
                        @ColumnResult(name = "usuario_id", type = Long.class),
                        @ColumnResult(name = "usuario_email"),
                        @ColumnResult(name = "usuario_nome")
                })
)

@Entity
public class Produto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NotBlank
    private String nome;

    private String descricao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario locatario;

    @NotBlank
    private String local;

    @NotNull
    private Double preco;

    private Double avaliacao;

    @OneToMany(mappedBy = "produto")
    private List<AluguelProduto> alugueis;

    public Produto() {}

    public Produto(String nome, String descricao, Usuario locatario, String local, Double preco, Double avaliacao) {
        this.nome = nome;
        this.descricao = descricao;
        this.locatario = locatario;
        this.local = local;
        this.preco = preco;
        this.avaliacao = avaliacao;
    }

    public List<AluguelProduto> getAlugueis() {
        return alugueis;
    }

    public void setAlugueis(List<AluguelProduto> alugueis) {
        this.alugueis = alugueis;
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

    public Usuario getLocatario() {
        return locatario;
    }

    public void setLocatario(Usuario locatario) {
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
