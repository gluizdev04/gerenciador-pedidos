package com.gluizdev04.gerenciador_pedidos.repository;

import com.gluizdev04.gerenciador_pedidos.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findByDataEntregaIsNull();
    List<Pedido> findByDataEntregaIsNotNull();
    List<Pedido> findByDataAfter(LocalDate data);
    List<Pedido> findByDataBefore(LocalDate data);
    @Query("SELECT p FROM Pedido p WHERE p.data BETWEEN :dataInicio AND :dataFim ")
    List<Pedido> pedidoEntrePeriodo(LocalDate dataInicio, LocalDate dataFim);
}
