package com.gluizdev04.gerenciador_pedidos.repository;

import com.gluizdev04.gerenciador_pedidos.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findByDataEntregaIsNull();
    List<Pedido> findByDataEntregaIsNotNull();
}
