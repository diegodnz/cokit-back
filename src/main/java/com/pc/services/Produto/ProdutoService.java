package com.pc.services.Produto;

import com.pc.configs.exceptions.MensagemException;
import com.pc.configs.security.JWTUtil;
import com.pc.dto.Produto.ProdutoInput;
import com.pc.dto.Produto.ProdutoOutput;
import com.pc.dto.Produto.ProdutoOutputListagem;
import com.pc.model.Produto;
import com.pc.model.Usuario;
import com.pc.repositories.ProdutoRepository;
import com.pc.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProdutoService {

    @Autowired
    private UsuarioRepository usuarioRepo;

    @Autowired
    private ProdutoRepository produtoRepo;

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private EntityManager em;

    private Usuario getUsuarioLogado(HttpServletRequest req) {
        Usuario logado = jwtUtil.getPessoaLogada(usuarioRepo, req);
        if (logado == null) {
            throw new MensagemException("Logar", HttpStatus.UNAUTHORIZED);
        }
        return logado;
    }

    public ResponseEntity<ProdutoOutput> cadastrar(ProdutoInput produto, HttpServletRequest req) {
        Usuario logado = getUsuarioLogado(req);

        Produto produtoEntitade = new Produto(produto.getNome(), logado, produto.getLocal(), produto.getPreco(), null);
        produtoRepo.save(produtoEntitade);

        ProdutoOutput produtoOutput = new ProdutoOutput(produtoEntitade.getId(), produtoEntitade.getNome(), logado, produtoEntitade.getLocal(), produtoEntitade.getPreco(), null);
        return new ResponseEntity<>(produtoOutput, HttpStatus.CREATED);
    }

    public ResponseEntity<Page<ProdutoOutputListagem>> listar(String paginaStr, String limitePaginaStr, String nome, HttpServletRequest req) {
        // Transformar dados string para para inteiro
        Pageable pageable;
        int limiteInt = 10;
        int paginaInt = 0;
        try {
            if (limitePaginaStr != null) {
                limiteInt = Integer.parseInt(limitePaginaStr);
            }
            if (paginaStr != null) {
                paginaInt = Integer.parseInt(paginaStr)-1;
            }
            pageable = PageRequest.of(paginaInt, limiteInt, Sort.by(Sort.Direction.DESC, "nome"));
        } catch(Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Fazer String de query
        StringBuilder queryString = new StringBuilder().append("SELECT p.id, p.avaliacao, p.local, p.nome, p.preco, p.usuario_id")
                .append(" FROM Produto p");
        queryString.append(" WHERE TRUE");
        if (nome != null) {
            queryString.append(" AND p.nome ilike '%'||:nome||'%'");
        }
        queryString.append(" ORDER BY p.nome DESC");

        Query query = em.createNativeQuery(queryString.toString(), "ProdutoOutListagem");

        if (nome != null) {
            query.setParameter("nome", nome);
        }

        // Fazer paginação
        List<ProdutoOutputListagem> result = query.getResultList();

        int primeiroIndice = paginaInt*limiteInt;
        if (primeiroIndice >= result.size()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        int ultimoIndiceItem = primeiroIndice+limiteInt;
        if (ultimoIndiceItem > result.size()) {
            ultimoIndiceItem = result.size();
        }

        result = result.subList(primeiroIndice, ultimoIndiceItem);
        Page<ProdutoOutputListagem> resultPage = new PageImpl<>(result, pageable, result.size());
        return new ResponseEntity<Page<ProdutoOutputListagem>>(resultPage, HttpStatus.OK);

    }

    private List<ProdutoOutputListagem> transformaParaDto(List<Produto> lista) {
        List<ProdutoOutputListagem> resultado = new ArrayList<>();
        for (Produto p : lista) {
            resultado.add(new ProdutoOutputListagem(p.getId(), p.getNome(), p.getLocatario(), p.getLocal(), p.getPreco(), p.getAvaliacao()));
        }
        return resultado;
    }
}
