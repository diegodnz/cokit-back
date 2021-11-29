package com.pc.services;

import javax.servlet.http.HttpServletRequest;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.pc.configs.security.JWTUtil;
import com.pc.dto.UsuarioLogin;
import com.pc.dto.UsuarioResposta;
import com.pc.dto.UsuarioRespostaToken;
import com.pc.model.Usuario;
import com.pc.repositories.UsuarioRepository;

@Service
public class UsuarioService {
	
	@Autowired	
	private UsuarioRepository UsuarioRepo;
	
	@Autowired
	private JWTUtil jwtUtil;
		
	public ResponseEntity<UsuarioResposta> cadastrar(Usuario usuarioRegistro) {
			
		Usuario UsuarioEmail = UsuarioRepo.findByEmail(usuarioRegistro.getEmail());
		if (UsuarioEmail != null) {
			return new ResponseEntity<UsuarioResposta>(HttpStatus.BAD_REQUEST);
		}
		
		usuarioRegistro.setSenha(BCrypt.hashpw(usuarioRegistro.getSenha(), BCrypt.gensalt()));
		
		UsuarioRepo.save(usuarioRegistro);
		UsuarioResposta resposta = new UsuarioResposta(usuarioRegistro.getId(), usuarioRegistro.getEmail(), usuarioRegistro.getNome());
		
		return new ResponseEntity<UsuarioResposta>(resposta, HttpStatus.CREATED);
	}
	
	public ResponseEntity<UsuarioRespostaToken> login(UsuarioLogin dadosLogin) {
		Usuario usuario = UsuarioRepo.findByEmail(dadosLogin.getEmail());
		if (usuario == null) {
			return new ResponseEntity<UsuarioRespostaToken>(HttpStatus.BAD_REQUEST);
		}
		
		boolean senhaCorreta = BCrypt.checkpw(dadosLogin.getSenha(), usuario.getSenha());
		if (!senhaCorreta) {
			return new ResponseEntity<UsuarioRespostaToken>(HttpStatus.BAD_REQUEST);
		} else {
			String token = jwtUtil.gerarToken(usuario.getEmail());
			UsuarioRespostaToken resposta = new UsuarioRespostaToken(usuario.getId(), usuario.getEmail(), usuario.getNome(), token);
			return new ResponseEntity<UsuarioRespostaToken>(resposta, HttpStatus.OK);	
		}
	}
	
	public ResponseEntity<String> getNomePessoaLogada(HttpServletRequest requisicao) {
		Usuario usuarioLogado = jwtUtil.getPessoaLogada(UsuarioRepo, requisicao);
		if (usuarioLogado == null) {
			return new ResponseEntity<String>("Usuário não logado", HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<String>(usuarioLogado.getNome(), HttpStatus.OK);
		}
	}
	
}
