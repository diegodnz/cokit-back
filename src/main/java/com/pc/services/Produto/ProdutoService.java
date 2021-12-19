package com.pc.services.Produto;

import com.pc.configs.exceptions.MensagemException;
import com.pc.configs.security.JWTUtil;
import com.pc.dto.AluguelProduto.IntervaloDatas;
import com.pc.dto.Produto.*;
import com.pc.dto.Usuario.UsuarioLocatarioDto;
import com.pc.model.AluguelProduto;
import com.pc.model.Produto;
import com.pc.model.Usuario;
import com.pc.repositories.AluguelProdutoRepository;
import com.pc.repositories.ProdutoRepository;
import com.pc.repositories.UsuarioRepository;
import com.pc.services.ServiceHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProdutoService {

    @Autowired
    private UsuarioRepository usuarioRepo;

    @Autowired
    private ProdutoRepository produtoRepo;

    @Autowired
    private AluguelProdutoRepository aluguelRepo;

    @Autowired
    private EntityManager em;

    @Autowired
    private ServiceHelper serviceHelper;

    public ResponseEntity<ProdutoOutput> cadastrar(ProdutoInput produto, HttpServletRequest req) {
        Usuario logado = serviceHelper.getUsuarioLogado(req);
        
        Produto produtoExistente = produtoRepo.findProdutoByNomeAndPrecoAndImagem(produto.getNome(), produto.getPreco(), produto.getImagem());
        if (produtoExistente != null) {
            throw new MensagemException("Produto já cadastrado", HttpStatus.BAD_REQUEST);
        }

        Produto produtoEntidade = new Produto(produto.getNome(), produto.getDescricao(), logado, produto.getLocal(), produto.getPreco(), null, produto.getImagem(), produto.getDataInicial(), produto.getDataFinal());
        produtoRepo.save(produtoEntidade);

        ProdutoOutput produtoOutput = new ProdutoOutput(produtoEntidade.getId(), produtoEntidade.getNome(), produtoEntidade.getDescricao(),logado.getId(), logado.getEmail(), logado.getNome(), produtoEntidade.getLocal(), produtoEntidade.getPreco(), null, new HashSet<>(), produtoEntidade.getImagem(), produtoEntidade.getDataInicial(), produtoEntidade.getDataFinal());
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
        return new ResponseEntity<>(new ProdutoOutput(produto.getId(), produto.getNome(), produto.getDescricao(), locatario.getId(), locatario.getEmail(), locatario.getNome(), produto.getLocal(), produto.getPreco(), produto.getAvaliacao(), datasIndisponiveis, produto.getImagem(), produto.getDataInicial(), produto.getDataFinal()), HttpStatus.OK);
    }

    public void alugarProduto(Long id, AlugarProdutoInput alugarInput, HttpServletRequest req) {
        Usuario logado = serviceHelper.getUsuarioLogado(req);

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
        int limiteInt = 10000;
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
        StringBuilder queryString = new StringBuilder().append("SELECT p.id, p.avaliacao, p.local, p.nome, p.preco, p.usuario_id, u.email as usuario_email, u.nome as usuario_nome, p.imagem as imagem")
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

    public List<ProdutoOutputListagem> produtosAnunciados(HttpServletRequest req) {
        Usuario logado = serviceHelper.getUsuarioLogado(req);

        List<Produto> produtos = produtoRepo.getByLocatario(logado.getId());
        List<ProdutoOutputListagem> produtosOutput = produtos.stream().map(item -> {
            return new ProdutoOutputListagem(item.getId(), item.getAvaliacao(), item.getLocal(), item.getNome(), item.getPreco(), logado.getId(), logado.getEmail(), logado.getNome(), item.getImagem());
        }).collect(Collectors.toList());

        return produtosOutput;
    }

    public List<ProdutoOutputAlugadosListagem> produtosAlugados(HttpServletRequest req) {
        Usuario logado = serviceHelper.getUsuarioLogado(req);

        List<AluguelProduto> alugueis = aluguelRepo.getByLocador(logado.getId());
        List<ProdutoOutputAlugadosListagem> alugueisAgrupados = agruparPorProduto(alugueis);

        return alugueisAgrupados;
    }

    public List<ProdutoOutputAlugadosLocatarioListagem> produtosAnunciadosAlugados(HttpServletRequest req) {
        Usuario logado = serviceHelper.getUsuarioLogado(req);

        List<Produto> anunciadosAlugados = produtoRepo.getAnunciadosAlugados(logado.getId());
        List<ProdutoOutputAlugadosLocatarioListagem> anunciadosAlugadosOutput = anunciadosAlugados.stream().map(produto -> {
            return new ProdutoOutputAlugadosLocatarioListagem(produto.getId(), produto.getAvaliacao(), produto.getLocal(), produto.getNome(), produto.getPreco(),
                    produto.getLocatario().getId(), produto.getLocatario().getEmail(), produto.getLocatario().getNome(), new HashMap<>(), produto.getImagem());
        }).collect(Collectors.toList());

        List<AluguelProduto> alugueis = aluguelRepo.getByLocatario(logado.getId());
        for (ProdutoOutputAlugadosLocatarioListagem p : anunciadosAlugadosOutput) {
            for (AluguelProduto aluguel :  alugueis) {
                if (aluguel.getProduto().getId() == p.getId()) {
                    UsuarioLocatarioDto locador = new UsuarioLocatarioDto(aluguel.getLocador().getId(), aluguel.getLocador().getEmail(), aluguel.getLocador().getNome());
                    if (!p.getMapAlugueisUsuarios().containsKey(locador)) {
                        p.getMapAlugueisUsuarios().put(locador, new ArrayList<>());
                    }
                    p.getMapAlugueisUsuarios().get(locador).add(new IntervaloDatas(aluguel.getDataInicial(), aluguel.getDataFinal()));
                }
            }
        }

        return anunciadosAlugadosOutput;
    }

    private List<ProdutoOutputAlugadosListagem> agruparPorProduto(List<AluguelProduto> alugueis) {
        Map<Long, List<IntervaloDatas>> produtosDatas = new HashMap<>();
        for (AluguelProduto aluguelProduto : alugueis) {
            if (produtosDatas.containsKey(aluguelProduto.getProduto().getId())) {
                produtosDatas.get(aluguelProduto.getProduto().getId()).add(new IntervaloDatas(aluguelProduto.getDataInicial(), aluguelProduto.getDataFinal()));
            }
            else {
                List<IntervaloDatas> listaDatas = new ArrayList<>();
                listaDatas.add(new IntervaloDatas(aluguelProduto.getDataInicial(), aluguelProduto.getDataFinal()));
                produtosDatas.put(aluguelProduto.getProduto().getId(), listaDatas);
            }
        }

        List<Produto> produtos = new ArrayList<>();
        for (Long produtoId : produtosDatas.keySet()) {
            produtos.add(produtoRepo.getById(produtoId));
        }

        List<ProdutoOutputAlugadosListagem> produtosAlugados = produtos.stream().map(produto -> {
            return new ProdutoOutputAlugadosListagem(produto.getId(), produto.getAvaliacao(), produto.getLocal(), produto.getNome(), produto.getPreco(), produto.getLocatario().getId(), produto.getLocatario().getEmail(), produto.getLocatario().getNome(), new ArrayList<>(), produto.getImagem());
        }).collect(Collectors.toList());

        for (ProdutoOutputAlugadosListagem p : produtosAlugados) {
            p.setDatasAlugadas(produtosDatas.get(p.getId()));
        }
        return produtosAlugados;
    }


}
