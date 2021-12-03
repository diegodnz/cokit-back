package com.pc.controllers;

import com.pc.dto.Produto.ProdutoInput;
import com.pc.dto.Produto.ProdutoOutput;
import com.pc.services.Produto.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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
}
