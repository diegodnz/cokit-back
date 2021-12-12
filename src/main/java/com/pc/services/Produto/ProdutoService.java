package com.pc.services.Produto;

import com.pc.configs.exceptions.MensagemException;
import com.pc.configs.security.JWTUtil;
import com.pc.dto.AluguelProduto.IntervaloDatas;
import com.pc.dto.Produto.AlugarProdutoInput;
import com.pc.dto.Produto.ProdutoInput;
import com.pc.dto.Produto.ProdutoOutput;
import com.pc.dto.Produto.ProdutoOutputListagem;
import com.pc.model.AluguelProduto;
import com.pc.model.Produto;
import com.pc.model.Usuario;
import com.pc.repositories.AluguelProdutoRepository;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
public class ProdutoService {

    @Autowired
    private UsuarioRepository usuarioRepo;

    @Autowired
    private ProdutoRepository produtoRepo;

    @Autowired
    private AluguelProdutoRepository aluguelRepo;

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

        Produto produtoEntidade = new Produto(produto.getNome(), produto.getDescricao(), logado, produto.getLocal(), produto.getPreco(), null);
        produtoRepo.save(produtoEntidade);

        ProdutoOutput produtoOutput = new ProdutoOutput(produtoEntidade.getId(), produtoEntidade.getNome(), produtoEntidade.getDescricao(),logado.getId(), logado.getEmail(), logado.getNome(), produtoEntidade.getLocal(), produtoEntidade.getPreco(), null, new HashSet<>());
        return new ResponseEntity<>(produtoOutput, HttpStatus.CREATED);
    }

    private HashSet<LocalDate> obterDatasIndisponiveis(Long produtoId) {
        List<AluguelProduto> datasAlugadas = aluguelRepo.getDatasAlugadas(produtoId, LocalDate.now());
        List<IntervaloDatas> intervalosAlugueis = new ArrayList<>();
        for (AluguelProduto aluguel : datasAlugadas) {
            intervalosAlugueis.add(new IntervaloDatas(aluguel.getDataInicial(), aluguel.getDataFinal()));
        }

        HashSet<LocalDate> datasAlugadasUmaVez = new HashSet<>();
        HashSet<LocalDate> datasIndisponiveis = new HashSet<>();
        for (IntervaloDatas intervalo : intervalosAlugueis) {
            LocalDate dataAtual = intervalo.getDataInicial();
            LocalDate dataFinal = intervalo.getDataFinal();
            while (!dataAtual.isAfter(dataFinal)) {
                System.out.println(dataAtual);
                if (datasAlugadasUmaVez.contains(dataAtual)) {
                    if (!datasIndisponiveis.contains(dataAtual)) {
                        datasIndisponiveis.add(dataAtual); // Só ficará indisponível caso seja alugada 2 vezes neste dia
                    }
                } else {
                    datasAlugadasUmaVez.add(dataAtual);
                }
                dataAtual = dataAtual.plusDays(1);
            }
        }
        return datasIndisponiveis;
    }

    public ResponseEntity<ProdutoOutput> verProduto(Long id) {
        Produto produto = produtoRepo.getById(id);
        if (produto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        HashSet<LocalDate> datasIndisponiveis = obterDatasIndisponiveis(produto.getId());
        Usuario locatario = usuarioRepo.getOne(produto.getLocatario().getId());
        return new ResponseEntity<>(new ProdutoOutput(produto.getId(), produto.getNome(), produto.getDescricao(), locatario.getId(), locatario.getEmail(), locatario.getNome(), produto.getLocal(), produto.getPreco(), produto.getAvaliacao(), datasIndisponiveis), HttpStatus.OK);
    }

    public void alugarProduto(Long id, AlugarProdutoInput alugarInput, HttpServletRequest req) {
        Usuario logado = getUsuarioLogado(req);

        Produto produto = produtoRepo.getById(id);
        if (produto == null) {
            throw new MensagemException("Produto não encontrado", HttpStatus.NOT_FOUND);
        }

        if (!alugarInput.getUnicoDia() && alugarInput.getDataFinal() == null) {
            throw new MensagemException("Por favor, preencha a data final", HttpStatus.BAD_REQUEST);
        }

        if (alugarInput.getUnicoDia()) {
            alugarInput.setDataFinal(alugarInput.getDataInicial());
        }

        AluguelProduto aluguel = new AluguelProduto(logado, alugarInput.getDataInicial(),alugarInput.getDataFinal(), produto);
        aluguelRepo.save(aluguel);
    }

    public ResponseEntity<Page<ProdutoOutputListagem>> listar(String paginaStr, String limitePaginaStr, String nome) {
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
            pageable = PageRequest.of(paginaInt, limiteInt, Sort.by(Sort.Direction.DESC, "avaliacao"));
        } catch(Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Fazer String de query
        StringBuilder queryString = new StringBuilder().append("SELECT p.id, p.avaliacao, p.local, p.nome, p.preco, p.usuario_id, u.email as usuario_email, u.nome as usuario_nome")
                .append(" FROM Produto p")
                .append(" INNER JOIN Usuario u")
                .append(" ON p.usuario_id = u.id");
        queryString.append(" WHERE TRUE");
        if (nome != null) {
            queryString.append(" AND p.nome ilike '%'||:nome||'%'");
        }
        queryString.append(" ORDER BY p.avaliacao DESC NULLS LAST");

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

}
