package com.pc.controllers;

import com.pc.dto.Produto.ProdutoInput;
import com.pc.dto.Produto.ProdutoOutput;
import com.pc.dto.Produto.ProdutoOutputListagem;
import com.pc.services.Produto.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Date;

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
}
