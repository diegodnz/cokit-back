package com.pc.repositories;

import com.pc.model.Mensagens;
import com.pc.model.Produto;
import com.pc.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MensagemRepository extends JpaRepository<Mensagens, Long> {
    Mensagens getById(Long id);

    @Query(value = "select * from mensagens where destinatario_id = :id or remetente_id = :id ORDER BY data_envio ASC", nativeQuery = true)
    List<Mensagens> getByUsuario(@Param("id") Long logadoId);
}
