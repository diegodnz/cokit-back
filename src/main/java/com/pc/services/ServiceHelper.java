package com.pc.services;

import com.pc.configs.exceptions.MensagemException;
import com.pc.configs.security.JWTUtil;
import com.pc.model.Usuario;
import com.pc.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class ServiceHelper {

    @Autowired
    private UsuarioRepository usuarioRepo;

    @Autowired
    private JWTUtil jwtUtil;

    public Usuario getUsuarioLogado(HttpServletRequest req) {
        Usuario logado = jwtUtil.getPessoaLogada(usuarioRepo, req);
        if (logado == null) {
            throw new MensagemException("Logar", HttpStatus.UNAUTHORIZED);
        }
        return logado;
    }
}
