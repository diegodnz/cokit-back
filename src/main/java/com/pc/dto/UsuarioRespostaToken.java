package com.pc.dto;

public class UsuarioRespostaToken {
		
		private Long id;
		
		private String email;
		
		private String nome;
		
		private String token;

		public UsuarioRespostaToken(Long id, String email, String nome, String token) {
			this.id = id;
			this.email = email;
			this.nome = nome;
			this.token = token;
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

		public String getToken() {
			return token;
		}

		public void setToken(String token) {
			this.token = token;
		}

	}
