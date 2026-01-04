package com.gluizdev04.gerenciador_pedidos.repository;

import com.gluizdev04.gerenciador_pedidos.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    Optional<Produto> findByNomeIgnoreCase(String nome);

    List<Produto> findByCategoriaNomeIgnoreCase(String nome);

    @Query("SELECT p FROM Produto p WHERE p.preco > :valor")
    List<Produto> buscarProdutoAPartirDeUmValor(Double valor);

    List<Produto> findByPrecoLessThan(double valor);

    Optional<Produto> findByNomeContainingIgnoreCase(String nome);

    List<Produto> findByCategoriaNomeIgnoreCaseOrderByPrecoAsc(String nome);

    List<Produto> findByCategoriaNomeIgnoreCaseOrderByPrecoDesc(String nome);

    long countByCategoriaNomeIgnoreCase(String categoriaNome);

    long countByPrecoGreaterThan(Double preco);

    List<Produto> findByPrecoLessThanOrNomeContainingIgnoreCase(Double preco, String nome);

    List<Produto> findTop3ByOrderByPrecoDesc();

    List<Produto> findTop5ByCategoriaNomeIgnoreCaseOrderByPrecoAsc(String categoria);

    @Query("SELECT p FROM Produto p ORDER BY p.preco ASC")
    List<Produto> ordenarPorValorCrescente();

    @Query("SELECT p FROM Produto p ORDER BY p.preco DESC")
    List<Produto> ordenarPorValorDecrescente();
}
