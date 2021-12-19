package com.pc.repositories;

import com.pc.model.AvaliacaoProduto;
import com.pc.model.Produto;
import com.pc.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AvaliacaoProdutoRepository extends JpaRepository<AvaliacaoProduto, Long> {

    AvaliacaoProduto getByAvaliadorAndProduto(Usuario avaliador, Produto produto);

    @Query(value = "SELECT AVG(avaliacao) FROM avaliacao_produto WHERE produto_id = :id", nativeQuery = true)
    Double getMediaAvaliacaoProduto(@Param("id") Long produtoId);
}
