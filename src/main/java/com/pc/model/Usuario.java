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
	private List<Produto> produtosAnunciados;

	@CPF
	@Column(unique = true)
	@NotBlank
	private String cpf;
	
	@Email
	@Column(unique = true)
	@NotBlank
	private String email;
	
	@Size(min = 4, message = "O nome deve conter 4 caracteres no mínimo")
	@NotBlank
	private String nome;
	
	@Size(min = 6, message = "A senha deve conter 6 caracteres no mínimo")
	@NotBlank
	private String senha;

	@OneToMany(mappedBy = "locador")
	private List<AluguelProduto> alugueis;

	@OneToMany(mappedBy = "remetente")
	private List<Mensagens> mensagensEnviadas;

	@OneToMany(mappedBy = "destinatario")
	private List<Mensagens> mensagensRecebidas;

	public Usuario() {}

	public Usuario(Long id, String cpf, String email, String nome, String senha) {
		this.id = id;
		this.cpf = cpf;
		this.email = email;
		this.nome = nome;
		this.senha = senha;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
}
