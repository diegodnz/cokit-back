package com.pc.controllers;

import com.pc.dto.Produto.*;
import com.pc.services.Produto.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    ProdutoService produtoService;

    // ** Cadastrar Produto **
    @PostMapping
    public ResponseEntity<ProdutoOutput> cadastrar(@Valid @RequestBody ProdutoInput produto, HttpServletRequest req) {
        return produtoService.cadastrar(produto, req);
    }

    // ** Listar Produtos **
    @GetMapping
    public ResponseEntity<Page<ProdutoOutputListagem>> listar(@RequestParam(name = "pagina", required = false) String pagina,
                                                              @RequestParam(name = "limite", required = false) String limitePagina,
                                                              @RequestParam(name = "titulo", required = false) String titulo
                                                              ) {
        return produtoService.listar(pagina, limitePagina, titulo);
    }

    // ** Ver produto **
    @GetMapping("/{id}")
    public ResponseEntity<ProdutoOutput> verProduto(@PathVariable Long id) {
        return produtoService.verProduto(id);
    }

    // ** Alugar produto **
    @PostMapping("/{id}/alugar")
    @ResponseStatus(HttpStatus.OK)
    public void alugarProduto(@PathVariable Long id, @RequestBody @Valid AlugarProdutoInput alugarInput, HttpServletRequest req) {
        produtoService.alugarProduto(id, alugarInput, req);
    }

    // ** Ver produtos anunciados **
    @GetMapping("/anuncios")
    @ResponseStatus(HttpStatus.OK)
    public List<ProdutoOutputListagem> verProdutosAnunciados(HttpServletRequest req) {
        return produtoService.produtosAnunciados(req);
    }

    // ** Ver produtos que alugou **
    @GetMapping("/alugados")
    @ResponseStatus(HttpStatus.OK)
    public List<ProdutoOutputAlugadosListagem> verProdutosAlugados(HttpServletRequest req) {
        return produtoService.produtosAlugados(req);
    }

    // ** Ver produtos anunciados e alugados **
    @GetMapping("/anuncios/alugueis")
    @ResponseStatus(HttpStatus.OK)
    public List<ProdutoOutputAlugadosLocatarioListagem> verProdutosAnunciadosAlugados(HttpServletRequest req) {
        return produtoService.produtosAnunciadosAlugados(req);
    }

    // ** Avaliar produto **
    @PostMapping("/avaliar/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void avaliar(@RequestBody @Valid AvaliacaoProdutoInput avaliacao, @PathVariable Long id, HttpServletRequest req) {
        produtoService.avaliarProduto(avaliacao, id, req);
    }
}
