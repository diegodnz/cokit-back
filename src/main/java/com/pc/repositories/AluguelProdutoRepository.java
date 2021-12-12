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

    @Query(value = "SELECT * from aluguel_produto where produto_id = :id and data_final >= :now", nativeQuery = true)
    List<AluguelProduto> getDatasAlugadas(@Param("id") Long id, @Param("now") LocalDate now);
}
