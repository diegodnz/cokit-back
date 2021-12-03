package com.pc.services.Produto;

import com.pc.configs.exceptions.MensagemException;
import com.pc.configs.security.JWTUtil;
import com.pc.dto.Produto.ProdutoInput;
import com.pc.dto.Produto.ProdutoOutput;
import com.pc.model.Produto;
import com.pc.model.Usuario;
import com.pc.repositories.ProdutoRepository;
import com.pc.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class ProdutoService {

    @Autowired
    private UsuarioRepository usuarioRepo;

    @Autowired
    private ProdutoRepository produtoRepo;

    @Autowired
    private JWTUtil jwtUtil;

    public ResponseEntity<ProdutoOutput> cadastrar(ProdutoInput produto, HttpServletRequest req) {
        Usuario logado = jwtUtil.getPessoaLogada(usuarioRepo, req);
        if (logado == null) {
            throw new MensagemException("Sem permissão", HttpStatus.UNAUTHORIZED);
        }

        Produto produtoEntitade = new Produto(produto.getNome(), logado, produto.getLocal(), produto.getPreco(), null);
        produtoRepo.save(produtoEntitade);

        ProdutoOutput produtoOutput = new ProdutoOutput(produtoEntitade.getId(), produtoEntitade.getNome(), logado, produtoEntitade.getLocal(), produtoEntitade.getPreco(), null);
        return new ResponseEntity<>(produtoOutput, HttpStatus.CREATED);
    }
}
