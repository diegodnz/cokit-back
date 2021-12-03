package com.pc.dto.Usuario;

import javax.validation.constraints.NotBlank;

public class UsuarioLogin {
	
	@NotBlank
	String email;
	
	@NotBlank
	String senha;

	public UsuarioLogin(String email, String senha) {
		this.email = email;
		this.senha = senha;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	

}
