package com.pc.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pc.dto.Usuario.UsuarioLogin;
import com.pc.dto.Usuario.UsuarioResposta;
import com.pc.dto.Usuario.UsuarioRespostaToken;
import com.pc.model.Usuario;
import com.pc.services.Usuario.UsuarioService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
		
	@Autowired
	private UsuarioService usuarioService;
	
	// ** Cadastrar Usuário **
	@PostMapping
	public ResponseEntity<UsuarioRespostaToken> register(@Valid @RequestBody Usuario usuarioEntrada, HttpServletRequest req) {
		return usuarioService.cadastrar(usuarioEntrada);
	}
	
	// ** Login (Receber Bearer Token) **
	@PostMapping("/login")
	public ResponseEntity<UsuarioRespostaToken> login(@Valid @RequestBody UsuarioLogin dadosLogin) {
		return usuarioService.login(dadosLogin);
	}
	
	// ** Teste **
	@GetMapping("/teste")
	public ResponseEntity<String> getNomePessoaLogada(HttpServletRequest requisicao) {
		return usuarioService.getNomePessoaLogada(requisicao);
	}

}
