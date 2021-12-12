package com.pc.services.Mensagem;

import com.pc.configs.exceptions.MensagemException;
import com.pc.dto.Mensagem.Chat;
import com.pc.dto.Mensagem.MensagemInput;
import com.pc.model.Mensagens;
import com.pc.model.Usuario;
import com.pc.repositories.MensagemRepository;
import com.pc.repositories.UsuarioRepository;
import com.pc.services.ServiceHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class MensagemService {

    @Autowired
    private ServiceHelper serviceHelper;

    @Autowired
    private UsuarioRepository usuarioRepo;

    @Autowired
    private MensagemRepository mensagemRepository;

    public void enviar(MensagemInput mensagemInput, HttpServletRequest req) {
        Usuario logado = serviceHelper.getUsuarioLogado(req);
        Usuario destinatario = usuarioRepo.getById(mensagemInput.getDestinatarioID());
        if (destinatario == null) {
            throw new MensagemException("Destinatário não encontrado", HttpStatus.BAD_REQUEST);
        }

        if (logado == destinatario) {
            throw new MensagemException("Não é possível enviar mensagem para si mesmo", HttpStatus.BAD_REQUEST);
        }

        Mensagens mensagem = new Mensagens(logado, destinatario, mensagemInput.getMensagem(), LocalDateTime.now());
        mensagemRepository.save(mensagem);
    }

    public List<Chat> mensagensRecebidas(HttpServletRequest req) {
        Usuario logado = serviceHelper.getUsuarioLogado(req);
        List<Mensagens> mensagens = mensagemRepository.getByDestinatario(logado);
        return agruparPorRemetente(mensagens);

    }

    private List<Chat> agruparPorRemetente(List<Mensagens> mensagens) {
        Map<Long, Chat> chats = new HashMap<>();
        for (Mensagens msg : mensagens) {
            Long idRemetente = msg.getRemetente().getId();
            if (chats.containsKey(idRemetente)) {
                chats.get(idRemetente).addMensagem(msg);
            } else {
                List<Mensagens> mensagemLista = new ArrayList<>();
                mensagemLista.add(msg);
                chats.put(idRemetente, new Chat(msg.getRemetente().getId(), msg.getRemetente().getEmail(), msg.getRemetente().getNome(), mensagemLista));
            }
        }
        List<Chat> chatsLista = new ArrayList<>();
        chatsLista.addAll(chats.values());
        return chatsLista;
    }
}
