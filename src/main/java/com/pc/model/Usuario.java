package com.pc.model;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import org.hibernate.validator.constraints.br.CPF;

import java.util.List;

@Entity
public class Usuario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonProperty(access = Access.READ_ONLY)
	private Long id;

	@OneToMany(mappedBy = "locatario")
	@JsonProperty(access = Access.READ_ONLY)
	private List<Produto> produtosAnunciados;

	private String nome;

	private String cpf;

	private String identidade;

	private String email;

	private String celular;

	private String usuario;

	private String senha;

	@OneToMany(mappedBy = "locador")
	@JsonProperty(access = Access.READ_ONLY)
	private List<AluguelProduto> alugueis;

	@OneToMany(mappedBy = "remetente")
	@JsonProperty(access = Access.READ_ONLY)
	private List<Mensagens> mensagensEnviadas;

	@OneToMany(mappedBy = "destinatario")
	@JsonProperty(access = Access.READ_ONLY)
	private List<Mensagens> mensagensRecebidas;

	public Usuario() {}

	public Usuario(String nome, String cpf, String identidade, String email, String celular, String usuario, String senha) {
		this.nome = nome;
		this.cpf = cpf;
		this.identidade = identidade;
		this.email = email;
		this.celular = celular;
		this.usuario = usuario;
		this.senha = senha;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Produto> getProdutosAnunciados() {
		return produtosAnunciados;
	}

	public void setProdutosAnunciados(List<Produto> produtosAnunciados) {
		this.produtosAnunciados = produtosAnunciados;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getIdentidade() {
		return identidade;
	}

	public void setIdentidade(String identidade) {
		this.identidade = identidade;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public List<AluguelProduto> getAlugueis() {
		return alugueis;
	}

	public void setAlugueis(List<AluguelProduto> alugueis) {
		this.alugueis = alugueis;
	}

	public List<Mensagens> getMensagensEnviadas() {
		return mensagensEnviadas;
	}

	public void setMensagensEnviadas(List<Mensagens> mensagensEnviadas) {
		this.mensagensEnviadas = mensagensEnviadas;
	}

	public List<Mensagens> getMensagensRecebidas() {
		return mensagensRecebidas;
	}

	public void setMensagensRecebidas(List<Mensagens> mensagensRecebidas) {
		this.mensagensRecebidas = mensagensRecebidas;
	}
}
