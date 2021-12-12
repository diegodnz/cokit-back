package com.pc.repositories;

import com.pc.model.Mensagens;
import com.pc.model.Produto;
import com.pc.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MensagemRepository extends JpaRepository<Mensagens, Long> {
    Mensagens getById(Long id);
    List<Mensagens> getByDestinatario(Usuario destinatario);
}
