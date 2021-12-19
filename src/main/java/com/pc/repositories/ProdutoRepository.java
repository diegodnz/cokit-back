package com.pc.repositories;


import com.pc.model.Produto;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    Produto getById(Long id);

    @Query(value = "select * from produto where usuario_id = :id ORDER BY avaliacao DESC NULLS LAST", nativeQuery = true)
    List<Produto> getByLocatario(@Param("id") Long locatarioId);

    @Query(value = "select * from produto where usuario_id = :id and EXISTS (select * from aluguel_produto where produto.id = produto_id)", nativeQuery = true)
    List<Produto> getAnunciadosAlugados(@Param("id") Long locatarioId);

    Produto findProdutoByNomeAndPrecoAndImagem(String nome, Double preco, String imagem);

}
