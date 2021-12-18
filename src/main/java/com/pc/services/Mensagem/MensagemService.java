package com.pc.services.Mensagem;

import com.pc.configs.exceptions.MensagemException;
import com.pc.dto.Mensagem.Chat;
import com.pc.dto.Mensagem.MensagemInput;
import com.pc.dto.Mensagem.MensagemOutput;
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
        List<Mensagens> mensagens = mensagemRepository.getByUsuario(logado.getId());
        return agruparPorRemetente(mensagens, logado);

    }

    private List<Chat> agruparPorRemetente(List<Mensagens> mensagens, Usuario logado) {
        Map<Long, Chat> chats = new HashMap<>();
        for (Mensagens msg : mensagens) {
            boolean enviouMensagem = msg.getRemetente().getId() == logado.getId();
            Usuario outroUsuario;
            if (enviouMensagem) {
                outroUsuario = msg.getDestinatario();
            } else {
                outroUsuario = msg.getRemetente();
            }

            if (chats.containsKey(outroUsuario.getId())) {
                chats.get(outroUsuario.getId()).addMensagem(new MensagemOutput(msg.getMensagem(), msg.getDataEnvio(), enviouMensagem));
            } else {
                List<MensagemOutput> mensagemLista = new ArrayList<>();
                mensagemLista.add(new MensagemOutput(msg.getMensagem(), msg.getDataEnvio(), enviouMensagem));
                chats.put(outroUsuario.getId(), new Chat(outroUsuario.getId(), outroUsuario.getEmail(), outroUsuario.getNome(), mensagemLista));
            }
        }
        List<Chat> chatsLista = new ArrayList<>();
        chatsLista.addAll(chats.values());
        return chatsLista;
    }
}
