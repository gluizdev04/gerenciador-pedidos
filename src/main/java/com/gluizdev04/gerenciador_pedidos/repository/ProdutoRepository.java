package com.gluizdev04.gerenciador_pedidos.repository;

import com.gluizdev04.gerenciador_pedidos.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    Optional<Produto> findByNomeIgnoreCase(String nome);
    List<Produto> findByCategoriaNomeIgnoreCase(String nome);
    List<Produto> findByPrecoGreaterThan(Double valor);
    List<Produto> findByPrecoLessThan(double valor);
    Optional<Produto> findByNomeContainingIgnoreCase(String nome);
    List<Produto> findByCategoriaNomeIgnoreCaseOrderByPrecoAsc(String nome);
    List<Produto> findByCategoriaNomeIgnoreCaseOrderByPrecoDesc(String nome);
    long countByCategoriaNomeIgnoreCase(String categoriaNome);
    long countByPrecoGreaterThan(Double preco);
    List<Produto> findByPrecoLessThanOrNomeContainingIgnoreCase(Double preco, String nome);
}
