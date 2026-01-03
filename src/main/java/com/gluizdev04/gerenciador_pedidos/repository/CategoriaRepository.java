package com.gluizdev04.gerenciador_pedidos.repository;

import com.gluizdev04.gerenciador_pedidos.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
}
