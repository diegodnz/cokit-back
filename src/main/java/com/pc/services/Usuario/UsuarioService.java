package com.pc.services.Usuario;

import javax.servlet.http.HttpServletRequest;

import com.pc.configs.exceptions.MensagemException;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.pc.configs.security.JWTUtil;
import com.pc.dto.Usuario.UsuarioLogin;
import com.pc.dto.Usuario.UsuarioResposta;
import com.pc.dto.Usuario.UsuarioRespostaToken;
import com.pc.model.Usuario;
import com.pc.repositories.UsuarioRepository;

@Service
public class UsuarioService {
	
	@Autowired	
	private UsuarioRepository UsuarioRepo;
	
	@Autowired
	private JWTUtil jwtUtil;
		
	public ResponseEntity<UsuarioRespostaToken> cadastrar(Usuario usuarioRegistro) {
			
		Usuario usuarioEmail = UsuarioRepo.findByEmail(usuarioRegistro.getEmail());
		if (usuarioEmail != null) {
			throw new MensagemException("Email já existe", HttpStatus.BAD_REQUEST);
		}

		Usuario usuarioCpf = UsuarioRepo.findByCpf(usuarioRegistro.getCpf());
		if (usuarioCpf != null) {
			throw new MensagemException("CPF já existe", HttpStatus.BAD_REQUEST);
		}

		String senhaSemCriptografia = usuarioRegistro.getSenha();
		usuarioRegistro.setSenha(BCrypt.hashpw(usuarioRegistro.getSenha(), BCrypt.gensalt()));
		
		UsuarioRepo.save(usuarioRegistro);
		
		return login(new UsuarioLogin(usuarioRegistro.getEmail(), senhaSemCriptografia));
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
