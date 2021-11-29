package com.pc.configs.security;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.pc.model.Usuario;
import com.pc.repositories.UsuarioRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTUtil {
	
	@Value("${jwt.secret}")
	private String secret;

	@Value("${jwt.expiration}")
	private Long expiration;
	
	public String gerarToken(String email) {
		return Jwts.builder()
				.setSubject(email)		
				.setExpiration(new Date(System.currentTimeMillis() + expiration))
				.signWith(SignatureAlgorithm.HS512, secret.getBytes())
				.compact();
	}

	public Usuario getPessoaLogada(UsuarioRepository userRepo , HttpServletRequest req) {
		try {
			String token = (String)req.getHeader("Authorization");
			return TokenValido(userRepo, token);		
		} catch (Exception e) {	
			return null;
		}
	}
	
	public Usuario TokenValido(UsuarioRepository userRepo, String token) throws Exception {
		Claims claims = getClaims(token);
		
		if (claims != null) {
			String email = claims.getSubject();
			Usuario usuario = userRepo.findByEmail(email);
			Date expirationDate = claims.getExpiration();
			Date now = new Date(System.currentTimeMillis());
			if (expirationDate != null && now.after(expirationDate) || usuario == null) { 										
				throw new Exception("Token inválido");				
			} else {
				return usuario;
			}
		}		
		
		return null;
	}
	
	public Claims getClaims(String token) {
		try {			
			return Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody();
		}
		catch (Exception e) {			
			return null;
		}
	}

}