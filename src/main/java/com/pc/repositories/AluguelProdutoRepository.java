package com.pc.repositories;

import com.pc.model.AluguelProduto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AluguelProdutoRepository extends JpaRepository<AluguelProduto, Long> {

    @Query(value = "select * from aluguel_produto where produto_id = :id and data_final >= :now", nativeQuery = true)
    List<AluguelProduto> getDatasAlugadas(@Param("id") Long id, @Param("now") LocalDate now);

    @Query(value = "select * from aluguel_produto where locador_id = :id ORDER BY data_inicial DESC", nativeQuery = true)
    List<AluguelProduto> getByLocador(@Param("id") Long locadorId);

    @Query(value = "select * from aluguel_produto alug inner join produto p on p.id = alug.produto_id where p.usuario_id = :id", nativeQuery = true)
    List<AluguelProduto> getByLocatario(@Param("id") Long locatarioId);
}
