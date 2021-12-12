package com.pc.controllers;

import com.pc.dto.Mensagem.Chat;
import com.pc.dto.Mensagem.MensagemInput;
import com.pc.services.Mensagem.MensagemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/mensagens")
public class MensagemController {

    @Autowired
    private MensagemService mensagemService;

    // ** Enviar mensagem **
    @PostMapping
    public void enviar(@RequestBody @Valid MensagemInput mensagem, HttpServletRequest req) {
        mensagemService.enviar(mensagem, req);
    }

    // ** Mensagens recebidas **
    @GetMapping
    public List<Chat> mensagensRecebidas(HttpServletRequest req) {
        return mensagemService.mensagensRecebidas(req);
    }
}
